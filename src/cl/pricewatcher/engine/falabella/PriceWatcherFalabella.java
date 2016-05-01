package cl.pricewatcher.engine.falabella;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PriceWatcherFalabella {

	private static String urlBase = "http://www.falabella.com";

	public final static String SEP = "&";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc;

		try {

			// need http
			// protocolhttp://www.falabella.com/falabella-cl/browse/siteMap.jsp
			doc = Jsoup
					.connect(
							"http://www.falabella.com/falabella-cl/category/cat70057/Notebooks?passedNavAction=push&parentCategoryId=cat2068")
					.userAgent("Mozilla").get();
			// http://www.falabella.com/falabella-cl/category/cat70057/Notebooks?passedNavAction=push&parentCategoryId=cat2068
			// get page title
			String title = doc.title();
			System.out.println("title : " + title);

			// get all links
			// Elements links = doc.select("a[href] ");
			getProductsInList4x4("http://www.falabella.com/falabella-cl/category/cat70057/Notebooks?passedNavAction=push&parentCategoryId=cat2068");

			/*
			 * Elements links = doc.getElementsByClass("linkgrismapasitio");
			 * 
			 * 
			 * for (Element link : links) {
			 * 
			 * if( link.text().contains("Computadores")){
			 * 
			 * getProductsInList4x4(getUrlBase()+link.attr("href"));
			 * 
			 * }
			 * 
			 * }
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Document getDoc(String URL) throws IOException {

		Document doc = Jsoup.connect(URL).userAgent("Mozilla").get();
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);

		return doc;
	}

	public Elements getLinksFromDoc(Document doc, String cssClass)
			throws IOException {

		Elements links = doc.getElementsByClass(cssClass);

		return links;
	}

	public Element getSpecificLinkFromDoc(Document doc, String cssClass,
			String filter) throws IOException {

		Elements links = doc.getElementsByClass(cssClass);

		for (Element link : links) {
			if (link.text().contains(filter)) {
				return link;
			}
		}
		return null;

	}

	/*
	 * view-source:http://www.falabella.com/falabella-cl/browse/productList.jsp?
	 * _dyncharset=iso-8859-1
	 * &requestChainToken=1304746894&goToPage=3&pageSize=16&priceFlag=
	 * &categoryId=cat70057&docSort=numprop&docSortProp=price
	 * &docSortOrder=ascending&onlineStoreFilter=online
	 * &userSelectedFormat=4*4&trail=SRCH%3Acat70057
	 * &navAction=jump&searchCategory=true&question=cat70057
	 * &searchColorGroupFacet=false++&qfh_s_s=submit
	 * &_D%3Aqfh_s_s=+&qfh_ft=SRCH%
	 * 3Acat70057&_D%3Aqfh_ft=+&_DARGS=%2Ffalabella-cl
	 * %2Fbrowse%2FfacetsFunctions.jsp.searchFacetsForm
	 * 
	 * <form id="facetsForm" name="facetsForm"
	 * action="/falabella-cl/browse/productList.jsp" method="get"> <input
	 * name="_dyncharset" value="iso-8859-1" type="hidden"> </input><input
	 * value="8789290302172416027" type="hidden"> </input><input type="hidden"
	 * name="requestChainToken" value="1304746894" /> <input type="hidden"
	 * name="goToPage" value="3" id="goToPage" /> <input type="hidden"
	 * name="pageSize" value="16" id="pageSize" /> <input type="hidden"
	 * name="priceFlag" value="" id="priceFlag" /> <input type="hidden"
	 * name="categoryId" value="cat70057" id="categoryId" /> <input
	 * type="hidden" name="docSort" value="numprop" id="docSortParam" /> <input
	 * type="hidden" name="docSortProp" value="price" id="docSortPropParam" />
	 * <input type="hidden" name="docSortOrder" value="ascending"
	 * id="docSortOrderParam" /> <input type="hidden" name="onlineStoreFilter"
	 * value="online" id="onlineStoreFilter" /> <input type="hidden"
	 * name="userSelectedFormat" value="4*4" id="userSelectedFormat" /> <input
	 * type="hidden" id="trail" name="trail" value="SRCH:cat70057" /> <input
	 * type="hidden" name="navAction" value="jump" /> <input type="hidden"
	 * name="searchCategory" value="true" /> <input type="hidden"
	 * name="question" value="cat70057"/> <input type="hidden"
	 * name="searchColorGroupFacet" id="searchColorGroupFacet" value="false   "
	 * /> <input name="qfh_s_s" value="submit" type="hidden"> <input
	 * name="_D:qfh_s_s" value=" " type="hidden"> <input id="facetTrail"
	 * name="qfh_ft" value="SRCH:cat70057" type="hidden"> <input
	 * name="_D:qfh_ft" value=" " type="hidden"> <input name="_DARGS"
	 * value="/falabella-cl/browse/facetsFunctions.jsp.searchFacetsForm"
	 * type="hidden"> </input></form>
	 */
	public static String generateNextLink(Document doc) {

		String nextURL = "";
		// System.out.println("HTML "+doc.select("form[name=facetsForm]").first().toString());
		String url = doc.select("form[name=facetsForm]").first().attr("action");
		System.out.println("\nURL " + url);
		String chDyn = "_dyncharset=";
		String dyn = doc.select("input[name=_dyncharset]").first()
				.attr("value");
		System.out.println("Dyncharset " + dyn);
		String chreqCh = "requestChainToken=";
		String reqChain = doc.select("input[name=requestChainToken]").first()
				.attr("value");
		System.out.println("requestChainToken " + reqChain);
		String chGoTo = "goToPage=";
		String goToPage = doc.select("input[name=goToPage]").first()
				.attr("value");
		System.out.println("goToPage " + goToPage);

		if (!goToPage.isEmpty()) {
			int goToPageInt = Integer.parseInt(goToPage);

			goToPageInt++;

			goToPage = Integer.toString(goToPageInt);

		}

		String chPaSize = "pageSize=";
		String pageSize = doc.select("input[name=pageSize]").first()
				.attr("value");
		System.out.println("pageSize " + pageSize);
		String chCatId = "categoryId=";
		String categoryId = doc.select("input[name=categoryId]").first()
				.attr("value");
		System.out.println("categoryId " + categoryId);
		String chDocSo = "docSort=";
		String docSort = doc.select("input[name=docSort]").first()
				.attr("value");
		System.out.println("docSort " + docSort);
		String chDocSoPro = "docSortProp=";
		String docSortProp = doc.select("input[name=docSortProp]").first()
				.attr("value");
		System.out.println("docSortProp " + docSortProp);
		String chDocSoOrd = "docSortOrder=";
		String docSortOrder = doc.select("input[name=docSortOrder]").first()
				.attr("value");
		System.out.println("docSortOrder " + docSortOrder);
		String chOSF = "onlineStoreFilter=";
		String onlineSF = doc.select("input[name=onlineStoreFilter]").first()
				.attr("value");
		System.out.println("onlineStoreFilter " + onlineSF);
		String chUSF = "userSelectedFormat=";
		String userSelectedFormat = doc
				.select("input[name=userSelectedFormat]").first().attr("value");
		System.out.println("userSelectedFormat " + userSelectedFormat);
		String chTrail = "trail=";
		String trail = doc.select("input[name=trail]").first().attr("value")
				.trim();
		trail = trail.replace(":", "%3");
		System.out.println("trail " + trail);
		String chNavAc = "navAction=";
		String navAction = doc.select("input[name=navAction]").first()
				.attr("value").trim();
		System.out.println("navAction " + navAction);
		String chSC = "searchCategory=";
		String searchCategory = doc.select("input[name=searchCategory]")
				.first().attr("value").trim();
		System.out.println("searchCategory " + searchCategory);
		String chQues = "question=";
		String question = doc.select("input[name=question]").first()
				.attr("value").trim();
		System.out.println("question " + question);
		String chSeCGF = "searchColorGroupFacet=";
		String searchCGF = doc.select("input[name=searchColorGroupFacet]")
				.first().attr("value").trim();
		System.out.println("searchColorGroupFacet " + searchCGF);
		searchCGF = searchCGF + "+";

		String chQFHSS = "qfh_s_s=";
		String qfh_s_s = doc.select("input[name=qfh_s_s]").first()
				.attr("value").trim();
		System.out.println("qfh_s_s " + qfh_s_s);
		String chDQFHSS = "_D%3qfh_s_s=";
		String _Dqfh_s_s = doc.select("input[name=_D:qfh_s_s]").first()
				.attr("value").trim();
		System.out.println("_D:qfh_s_s " + _Dqfh_s_s);
		if (_Dqfh_s_s.isEmpty()) {
			_Dqfh_s_s = "+";
		}

		String chQFHFT = "qfh_ft=";
		String qfh_ft = doc.select("input[name=qfh_ft]").first().attr("value")
				.trim();
		qfh_ft = qfh_ft.replace(":", "%3").trim();
		System.out.println("qfh_ft " + qfh_ft);
		String chDQFHT = "_D%3qfh_ft=";
		if (qfh_ft.isEmpty()) {
			qfh_ft = "+";
		}
		String Dfh_ft = doc.select("input[name=_D:qfh_ft]").first()
				.attr("value");
		System.out.println("_D:qfh_ft " + Dfh_ft);
		String chDARGS = "_DARGS=";
		// String dargs=doc.select("input[name=_DARGS]").first().attr("value");
		String dargs = "%2Ffalabella-cl%2Fbrowse%2FfacetsFunctions.jsp.searchFacetsForm";
		dargs = dargs.replace("/", "%2").trim();
		System.out.println("_DARGS " + dargs);
		/*
		 * 
		 * http://www.falabella.com/falabella-cl/browse/productList.jsp?
		 * _dyncharset=iso-8859-1&requestChainToken=1310219211&
		 * goToPage=2&pageSize=16&priceFlag=&categoryId=cat70057&
		 * docSort=numprop&docSortProp=price&docSortOrder=ascending&
		 * onlineStoreFilter=online&userSelectedFormat=4*4&
		 * trail=SRCH%3Acat70057&navAction=push&searchCategory=true&
		 * question=cat70057&searchColorGroupFacet=false+&qfh_s_s=submit
		 * &_D%3Aqfh_s_s=+&qfh_ft=SRCH%3Acat70057&_D%3Aqfh_ft=+&_DARGS=
		 * %2Ffalabella-cl%2Fbrowse%2FfacetsFunctions.jsp.searchFacetsForm
		 * 
		 * http://www.falabella.com/falabella-cl/browse/productList.jsp?_dyncharset
		 * =iso-8859-1&requestChainToken=1310219211
		 * &goToPage=2&pageSize=16&priceFlag=&categoryId=cat70057&
		 * docSort=numprop&docSortProp=price&docSortOrder=ascending
		 * &onlineStoreFilter=online&userSelectedFormat=4*4&
		 * trail=SRCH%3Acat70057&navAction=push&searchCategory=true&
		 * question=cat70057&searchColorGroupFacet=false+++++&qfh_s_s=submit
		 * &_D%
		 * 3Aqfh_s_s=+&qfh_ft=SRCH%3Acat70057&_D%3Aqfh_ft=+&_DARGS=%2Ffalabella
		 * -cl%2Fbrowse%2FfacetsFunctions.jsp.searchFacetsForm
		 * 
		 * http://www.falabella.com//falabella-cl/browse/productList.jsp;jsessionid
		 * =A978CDCBC3075430040B0A880664E0DB.node20
		 * ?_dyncharset=iso-8859-1&requestChainToken=1310792366
		 * &goToPage=1&pageSize=16&categoryId=cat70057&
		 * docSort=numprop&docSortProp=price&docSortOrder=ascending&
		 * onlineStoreFilter=online&userSelectedFormat=4*4&
		 * trail=SRCH%3cat70057&navAction=push&searchCategory=true&
		 * question=cat70057&searchColorGroupFacet=false+&qfh_s_s=submit
		 * _D%3qfh_s_s=+&qfh_ft=SRCH%3cat70057&_D%3qfh_ft=
		 * &_DARGS=%2falabella-cl%2search%2includes%2search.jsp.searchForm
		 * 
		 * 
		 * http://www.falabella.com//falabella-cl/browse/productList.jsp;jsessionid
		 * =0020AD3A8AE4BE2A9D7C997D47FCA6FA.node47?
		 * _dyncharset=iso-8859-1&requestChainToken=1310951475
		 * &goToPage=1&pageSize=16&categoryId=cat70057
		 * &docSort=numprop&docSortProp=price&docSortOrder=ascending
		 * &onlineStoreFilter=online&userSelectedFormat=4*4&
		 * trail=SRCH%3cat70057&navAction=push&searchCategory=true&
		 * question=cat70057&searchColorGroupFacet=false &qfh_s_s=submit
		 * &_D%3qfh_s_s= &qfh_ft=SRCH%3cat70057&_D:qfh_ft= &_DARGS=
		 * %2falabella-cl%2search%2includes%2search.jsp.searchForm
		 */

		nextURL = PriceWatcherFalabella.getUrlBase() + url + "?" + chDyn + dyn
				+ SEP + chreqCh + reqChain + SEP + chGoTo + goToPage + SEP
				+ chPaSize + pageSize + SEP + chCatId + categoryId + SEP;
		/*
		 * chDocSo+docSort+SEP+ chDocSoPro+docSortProp+SEP+
		 * chDocSoOrd+docSortOrder+SEP+ chOSF+onlineSF+SEP+
		 * chUSF+userSelectedFormat+SEP+ chTrail+trail+SEP+
		 * chNavAc+navAction+SEP+ chSC+searchCategory+SEP+ chQues+question+SEP+
		 * chSeCGF+searchCGF+SEP+ chQFHSS+qfh_s_s+SEP+ chDQFHSS+_Dqfh_s_s+SEP+
		 * chQFHFT+qfh_ft+SEP+ chDQFHT+Dfh_ft+SEP+ chDARGS+dargs;
		 */

		return nextURL;

	}

	public static void getProductsInList4x4(String url) throws IOException {

		System.out.println("url" + url);
		Document doc = getDoc(url);

		Elements products = doc.getElementsByClass("cajaLP4x");

		int size = products.size();
		int counter = 0;

		for (Element prod : products) {
			System.out.println("\nNumero " + counter);

			System.out.println("Producto : "
					+ prod.getElementsByClass("detalle").get(0)
							.getElementsByAttribute("title").attr("title"));

			System.out.println("Marca : "
					+ prod.getElementsByClass("marca").get(0)
							.getElementsByAttribute("title").attr("title"));
			
			String linkProd = prod.getElementsByClass("quickview").get(0).getElementsByAttributeStarting("href").attr("href");
			System.out.println("Link Prod : "+linkProd );
							//.getElementsByAttribute("title").attr("href"));


			if (!prod.getElementsByClass("wishlistPrice1").get(0)
					.getElementsByClass("opUnica").isEmpty()) {
				System.out.println("Oportunidad Unica CMR  TRUE");
			}

			System.out.println("precio 1 : "
					+ prod.getElementsByClass("precio1").get(0).ownText());
			
			System.out.println("precio Normal: "
					+ prod.getElementsByClass("precio2").get(0).ownText());

			System.out.println("precio Normal3: "
					+ prod.getElementsByClass("precio3").get(0).ownText());
			
		getProductSpecifications(urlBase+linkProd);
			
			counter++;
			if (counter == size) {
				getProductsInList4x4(generateNextLink(doc));

			}
		}
	}
	
	public static void getProductSpecifications(String url) throws IOException {

		System.out.println("url " + url);
		Document doc = getDoc(url);

		String sku = doc.select("div[class=sku]")
				.first().attr("value").trim();
		
		Elements specs1 = doc.getElementsByAttributeValue("id", "fila1");
		Elements specs2 = doc.getElementsByAttributeValue("id", "fila2");
		for (Element spec : specs1) {
			
			System.out.println("\n Atributo: " + spec.select("th[class=contFT]").first().text().trim());
			System.out.println("Valor: " + spec.select("td[class=contFT]").first().text().trim());
			
		}
		for (Element spec : specs2) {
			
			System.out.println("\n Atributo: " + spec.select("th[class=contFT]").first().text().trim());
			System.out.println("Valor: " + spec.select("td[class=contFT]").first().text().trim());
			
		}
		
	}

	public static String getUrlBase() {
		return urlBase;
	}

	public static void setUrlBase(String urlBase) {
		PriceWatcherFalabella.urlBase = urlBase;
	}

}
