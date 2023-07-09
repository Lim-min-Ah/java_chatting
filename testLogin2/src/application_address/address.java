package application_address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javafx.scene.Node;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class address
{
    //* 공공데이타포털(http://www.data.go.kr) 오픈 API 이용
   
    // 서비스명 : 통합검색 5자리 우편번호 조회서비스
    // 새 우편번호(2015-08-01부터) 오픈 API 주소
    // http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll
   
    // [in] s : 검색어 (도로명주소[도로명/건물명] 또는 지번주소[동/읍/면/리])
    // [in] p : 읽어올 페이지(1부터), l : 한 페이지당 출력할 목록 수(최대 50까지)
    // [out] v[i*3 +0]=우편번호, v[i*3 +1]=도로명주소, v[i*3 +2]=지번주소, v.Count/3=표시할 목록 수
    // [out] n[0]=검색한 전체 목록(우편번호) 개수, n[1]=읽어온 페이지(1부터)
    // 반환값 : 에러메시지, null == OK  String find(String s, int p, int l, List<String> v, int[] n)
	public static void main(String[] args)
    {
        HttpURLConnection con = null;
        String s = null;
        int p = 0;
        int l = 0;
        List<String> v = null;
        int[] n = null;
        try
        {
            URL url = new URL(
            "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll"
            + "?ServiceKey=sFDK0GiktxRpy3zjgu7T2XvGFtgOBm51mCZ146osoliDG2bHl9G6c3NDz3f5Nvi%2Bm1A%2BgINJF0CBAyQ4%2Bsjhfw%3D%3D" // 서비스키
            + "&countPerPage=" + l // 페이지당 출력될 개수를 지정(최대 50)
            + "&currentPage=" + p // 출력될 페이지 번호
            + "&srchwrd=" + URLEncoder.encode(s,"UTF-8") // 검색어
            );

            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept-language", "ko");
           
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder bd = fac.newDocumentBuilder();
            Document doc = bd.parse(con.getInputStream());
           
            boolean bOk = false; // <successYN>Y</successYN> 획득 여부
            s = null; // 에러 메시지
           
            String nn;
            org.w3c.dom.Node nd;
            NodeList ns = doc.getElementsByTagName("cmmMsgHeader");
            if (ns.getLength() > 0)
            for (nd = ns.item(0).getFirstChild(); nd != null; nd = nd.getNextSibling())
            {
                nn = nd.getNodeName();
               
                if (!bOk)
                {
                    if (nn.equals("successYN")) // 성공 여부
                    {
                        if (nd.getTextContent().equals("Y")) bOk = true; // 검색 성공
                    }
                    else if (nn.equals("errMsg")) // 에러 메시지
                    {
                        s = nd.getTextContent();
                    }
                }
                else
                {
                    if (nn.equals("totalCount")) // 전체 검색수
                    {
                        n[0] = Integer.parseInt(nd.getTextContent());
                    }
                    else if (nn.equals("currentPage")) // 현재 페이지 번호
                    {
                        n[1] = Integer.parseInt(nd.getTextContent());
                    }
                }
            }
           
            if (bOk)
            {
                ns = doc.getElementsByTagName("newAddressListAreaCdSearchAll");
                for (p = 0; p < ns.getLength(); p++)
                for (nd = ns.item(p).getFirstChild(); nd != null; nd = nd.getNextSibling())
                {
                // nn = nd.getNodeName();
                // if (nn.equals("zipNo") || // 우편번호
                //  nn.equals("lnmAdres") || // 도로명 주소
                //  nn.equals("rnAdres")) // 지번 주소
                // {
                        v.add(nd.getTextContent());
                // }
                }
            }
           
            if (s == null)
            { // OK!
                if (v.size() < 3)
                    s = "검색결과가 없습니다.";
            }
        }
        catch(Exception e)
        {
            s = e.getMessage();
        }
       
        if (con != null)
            con.disconnect();

//        return s;
    }
}
//public class address {
//	public static void main(String[] args) throws IOException {
//        StringBuilder urlBuilder = new StringBuilder("http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=sFDK0GiktxRpy3zjgu7T2XvGFtgOBm51mCZ146osoliDG2bHl9G6c3NDz3f5Nvi%2Bm1A%2BgINJF0CBAyQ4%2Bsjhfw%3D%3D"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("srchwrd","UTF-8") + "=" + URLEncoder.encode("공평동", "UTF-8")); /*검색어*/
//        urlBuilder.append("&" + URLEncoder.encode("countPerPage","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지당 출력될 개수를 지정(최대50)*/
//        urlBuilder.append("&" + URLEncoder.encode("currentPage","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*출력될 페이지 번호*/
//        URL url = new URL(urlBuilder.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
//        BufferedReader rd;
//        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//        System.out.println(sb.toString());
//    }
//}
