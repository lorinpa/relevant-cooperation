/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.web.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import static org.pa.definitions.RequestDefinitions.JWT_ATTR_KEY;
import org.pa.repository.SystemPropertyRepository;
import org.pa.rss.FeedTask;
import org.pa.rss.FeedTypes;
import org.pa.rss.data.CoopNewsFeedData;
import org.pa.rss.data.DemAtWorkFeedData;
import org.pa.rss.data.DemCollabFeedData;
import org.pa.rss.data.FeedDataInterface;
import org.pa.rss.data.GoogleFeedData;
import org.pa.rss.data.RedditFeedData;
import org.pa.rss.data.UsFedCoopFeedData;

/**
 * Web application lifecycle listener.
 *
 * @author mwave
 */
@WebListener
public class AppListener implements ServletContextListener {
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String jwt_key = SystemPropertyRepository.getInstance().findJwtKey();
        sce.getServletContext().setAttribute(JWT_ATTR_KEY,jwt_key);
        /* get all the rss feeds at startup */
        initFeedData();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
    private void initFeedData() {
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(7);
        FeedTask dw = new FeedTask(DemAtWorkFeedData.getInstance(), FeedTypes.DEM_AT_WORK_RSS_URL);
        FeedTask usFed = new FeedTask(UsFedCoopFeedData.getInstance(), FeedTypes.US_FED_COOP_RSS_URL);
        FeedTask reddit = new FeedTask(RedditFeedData.getInstance(), FeedTypes.REDDIT_RSS_URL, FeedTypes.ATOM);
        FeedTask google = new FeedTask(GoogleFeedData.getInstance(), FeedTypes.GOOGLE_RSS_URL);
        FeedTask coopNews = new FeedTask(CoopNewsFeedData.getInstance(), FeedTypes.COOP_NEWS_RSS_URL);
        FeedTask demLab = new FeedTask(DemCollabFeedData.getInstance(),FeedTypes.DEM_COLLAB_RSS_URL);

      
        Collection<Callable<FeedDataInterface>> solvers = new ArrayList<>();
        solvers.add(dw);
        solvers.add(usFed);
        solvers.add(reddit);
        solvers.add(google);
        solvers.add(coopNews);
        solvers.add(demLab);

        CompletionService<FeedDataInterface> ecs = new ExecutorCompletionService<>(tpe);
        solvers.forEach((s) -> {
            ecs.submit(s);
        });
        tpe.shutdown();
    }
}
