package com.github.githubbers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

class repoPlug
{

    static ArrayList<String> getLink()
    {

        ArrayList<String> arrayList = new ArrayList<>();
        try
        {

            final Document document = Jsoup.connect("https://github.com/STIW3054-A191/Assignments/issues/1").get();
            Elements elements = document.select("table").select("a");

            for (Element array : elements)
            {
                String elementLink = array.attr("href");
                String theLink;

                if (!elementLink.startsWith("http://cwiki"))
                {

                    if (elementLink.endsWith(".git"))
                    {
                        theLink = elementLink.substring(0, elementLink.length() - 4);
                    } else
                        {
                        theLink = elementLink;
                    }

                    if (!arrayList.contains(theLink))
                    {
                        arrayList.add(theLink);
                    }
                }
            }
        } catch (IOException ie)
        {
            ie.printStackTrace();
        }
        return arrayList;
    }

}
