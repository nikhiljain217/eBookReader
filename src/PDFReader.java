import java.io.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import net.coobird.thumbnailator.*;



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
	

	public String getText(int numberOfPages)
	{
		String text = new String();
		try
		{
			PDDocument pdfReader = PDDocument.load(new File(this.pdfPath));
			text.concat((new PDFTextStripper()).getText(pdfReader));
			pdfReader.close();
		
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in getText function with error message "+e);
		}
		
		return text;
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
