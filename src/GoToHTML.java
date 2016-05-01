import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class GoToHTML {

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);

		HtmlPage page = webClient
				.getPage("http://www.falabella.com/falabella-cl/category/cat70057/Notebooks");
		String javaScriptCode = "javascript:goToPage(2);";

		Object result = page.executeJavaScript(javaScriptCode)
				.getJavaScriptResult();
		System.out.println(result);
	}

}
