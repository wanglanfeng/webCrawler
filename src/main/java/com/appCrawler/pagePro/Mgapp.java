package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Mgapp_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 应用酷  http://www.mgyapp.com/
 * Mgapp #119
 * @author DMT
 */
public class Mgapp implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		//index page					http://www.mgyapp.com/search
		if(page.getUrl().regex("http://www\\.mgyapp\\.com/search/.*").match()){
			//app的具体介绍页面											
			List<String> url1 = page.getHtml().links("//ul[@class='all-list']").regex("http://www\\.mgyapp\\.com/apps.*").all();

			//添加下一页url(翻页)
			List<String> url2 = page.getHtml().links("//div[@class='depage mt20 ac']").regex("http://www\\.mgyapp\\.com/search/.*").all();
			
			url1.addAll(url2);
			
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		
		//the app detail page
		else if(page.getUrl().regex("http://www\\.mgyapp\\.com/apps.*").match()){
//			Apk apk = null;
//			String appName = null;				//app名字
//			String appDetailUrl = null;			//具体页面url
//			String appDownloadUrl = null;		//app下载地址
//			String osPlatform = null ;			//运行平台
//			String appVersion = null;			//app版本
//			String appSize = null;				//app大小
//			String appUpdateDate = null;		//更新日期
//			String appType = null;				//下载的文件类型 apk？zip？rar？ipa?
//			String appvender = null;			//app开发者  APK这个类中尚未添加
//			String appDownloadedTime=null;		//app的下载次数
//			
//			String nameString=page.getHtml().xpath("//h1[@class='det-title']/text()").toString();			
//				appName =nameString;
//				
//			appDetailUrl = page.getUrl().toString();
//			
//			appDownloadUrl = page.getHtml().xpath("//li[@class='det-butn']/a[1]/@href").toString();
//			String platFormString =page.getHtml().xpath("//ul[@class='det-info-list']/li[6]/text()").toString();
//				osPlatform = platFormString.substring(platFormString.indexOf("：")+1,platFormString.length());
//		
//			String versionString = page.getHtml().xpath("//ul[@class='det-info-list']/li[1]/text()").toString();
//				appVersion = versionString.substring(versionString.indexOf("：")+1,versionString.length());
//			
//			String sizeString = page.getHtml().xpath("//ul[@class='det-info-list']/li[4]/text()").toString();
//				appSize = sizeString.substring(sizeString.indexOf("：")+1,sizeString.length());
//			
//			String updatedateString = page.getHtml().xpath("//ul[@class='det-info-list']/li[3]/text()").toString();
//				appUpdateDate = updatedateString.substring(updatedateString.indexOf("：")+1,updatedateString.length());
//			
//			String typeString = "apk";
//				appType =typeString;
//			
//			String venderString = page.getHtml().xpath("//ul[@class='det-info-list']/li[7]/text()").toString();
//				appvender = venderString.substring(venderString.indexOf("：")+1,venderString.length());;
//				
//			String DownloadedTimeString = page.getHtml().xpath("//ul[@class='det-info-list']/li[5]/text()").toString();
//				appDownloadedTime = DownloadedTimeString.substring(DownloadedTimeString.indexOf("：")+1,DownloadedTimeString.length());		
//			/*
//			System.out.println("appName="+appName);
//			System.out.println("appDetailUrl="+appDetailUrl);
//			System.out.println("appDownloadUrl="+appDownloadUrl);
//			System.out.println("osPlatform="+osPlatform);
//			System.out.println("appVersion="+appVersion);
//			System.out.println("appSize="+appSize);
//			System.out.println("appUpdateDate="+appUpdateDate);
//			System.out.println("appType="+appType);
//			System.out.println("appvender="+appvender);
//			System.out.println("appDownloadedTime="+appDownloadedTime);
//			 */
//			if(appName != null && appDownloadUrl != null){
//				apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
//			}
			return Mgapp_Detail.getApkDetail(page);
		}
		return null;
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
