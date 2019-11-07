import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class API2 {
	
	//tag의 값을 가져오는 메소드
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	
	public static void main(String[] args) {
		String Text;
		Scanner scan = new Scanner(System.in);
		System.out.println("단어 입력 : ");
		Text = scan.next(); //검색할 단어 입력
		
		try{
			while(true){
				// parsing할 url 지정(API 키 포함해서)
				String url = "https://stdict.korean.go.kr/api/search.do?key=593D606C0EEABE730CEDA57F7A527E4A&q="+Text;
				
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				
				// 최상위 태그 출력
				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				
				// �Ľ��� tag
				NodeList nList = doc.getElementsByTagName("item");
				if(nList.getLength() == 0)
				{
					System.out.println("검색 결과가 없습니다.");
				} else {
					System.out.println("파싱할 리스트 수 : "+ nList.getLength());
				}
				
				for(int temp = 0; temp < nList.getLength(); temp++){
					Node nNode = nList.item(temp);
				
						Element eElement = (Element) nNode;
						System.out.println("######################");
						//System.out.println(eElement.getTextContent());
						System.out.println("뜻  : " + getTagValue("definition", eElement));
				
					}	// for end

				break;
			}	// while end
			
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
	}
}
