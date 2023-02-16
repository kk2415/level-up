package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.KakaoJob;
import com.levelup.recruit.domain.enumeration.Company;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KakaoScraper {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    public List<Job> findJobs() {
        int page = 1;
        int lastPage = 5;
        String params;

        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();

        List<Job> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            params = "?skilset=Android,iOS,Windows,Web_front,DB,Cloud,Server,Hadoop_eco_system,Algorithm_Ranking,System&company=ALL&page=" + page;

            driver.get(Company.KAKAO.getUrl() + params);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> jobList = driver.findElements(By.cssSelector("ul.list_jobs a"));
            List<KakaoJob> scrapedJobs = jobList.stream().map(job -> {
                String title = job.findElement(By.cssSelector("h4.tit_jobs")).getText();
                final String url = job.getAttribute("href");
                final String noticeEndDate = job.findElement(By.cssSelector("dl.list_info > dd")).getText();

                return KakaoJob.of(title, url, noticeEndDate);
            }).collect(Collectors.toUnmodifiableList());

            jobs.addAll(scrapedJobs);
        }

        driver.quit();

        return jobs;
    }
}
