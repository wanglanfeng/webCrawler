package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PageProAngeeks_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 安极市场[中国] app搜索抓取 url:http://apk.angeeks.com/search?keywords=MT&x=0&y=0
 *
 * @version 1.0.0
 */
public class PageProAngeeks implements PageProcessor {

	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProAngeeks.class);

	// 定义网站编码，以及间隔时间
	Site site = Site.me().setCharset("gb2312")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	/**
	 * process the page, extract urls to fetch, extract the data and store
	 *
	 * @param page
	 */
	@Override
	public Apk process(Page page) {
		LOGGER.debug("crawler url: {}", page.getUrl());

		// 获取搜索页面
		if (page.getUrl().regex("http://apk\\.angeeks\\.com/search\\?.*")
				.match()) {
			LOGGER.debug("match success, url:{}", page.getUrl());
			System.out.println(page.getHtml().toString());

			// 获取详细链接，以及分页链接
			List<String> urlList = page.getHtml()
					.links("//ul[@id='mybou']/li/dl/dt").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pagenumb']").all();
			for (String url : pageList) {
				urlList.add(url);
			}
			 Set<String> cacheSet = Sets.newHashSet();
	    		cacheSet.addAll(urlList);
			for (String temp : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(temp)
						&& !temp.contains("http://apk.angeeks.com/downloadAPK.do"))
					page.addTargetRequest(temp);
			}
			// 打印搜索结果url
			LOGGER.debug("app info results urls: {}", page.getTargetRequests());
		}

		// 获取信息
		if (page.getUrl().regex("http://apk\\.angeeks\\.com/soft/.*").match()) {
			return PageProAngeeks_Detail.getApkDetail(page);


		}

		return null;
	}

	/**
	 * get the site settings
	 *
	 * @return site
	 * @see us.codecraft.webmagic.Site
	 */
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
