import java.io.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.FileInputStream;
import java.lang.*;
import java.util.HashMap;

import org.apache.pdfbox.*;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import net.coobird.thumbnailator.*;
import org.apache.commons.*;



public class PDFReader extends Reader{

	
	
	String pdfPath;
	
	public PDFReader(String path) throws IOException
	{
		pdfPath = path;
		
		
	}
	
	public int getTotalPageNumber()
	{
		
		int numberOfPages=0;
		
		try {
			PDDocument pdfReader = PDDocument.load(new File(this.pdfPath));
			numberOfPages = pdfReader.getNumberOfPages();
			pdfReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		return numberOfPages;
	}
	

	public HashMap<Integer,String> getText()
	{
		String text = new String();
		HashMap<Integer,String> pageMap =  new HashMap<Integer, String>();
		try
		{
			PdfReader pdfReaderHandle = new PdfReader(pdfPath);
			int pages = getTotalPageNumber();
			int i=2;
			while (i <= pages) {
				pageMap.put((Integer) i,PdfTextExtractor
						.getTextFromPage(pdfReaderHandle, i));
				
				
				i++;
				}
			pdfReaderHandle.close();
		
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in getText function with error message "+e);
		}
		
		return pageMap;
	}
	
	public BufferedImage getCover() throws IOException
	{
		PDDocument pdfReader = PDDocument.load(new File(this.pdfPath));
		PDFRenderer pdfRenderer = new PDFRenderer(pdfReader);
		
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
		bim = Thumbnails.of(bim).forceSize(150, 250).asBufferedImage();
		pdfReader.close();
		return bim;
		
	}
	
}
