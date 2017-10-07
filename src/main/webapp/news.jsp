<%-- 
    Document   : news
    Created on : Oct 4, 2017, 12:55:28 PM
    Author     : mwave
--%>

<%@page import="org.pa.rss.Node"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.pa.rss.data.*, org.pa.rss.NodeList, org.pa.rss.Node" %>
<!DOCTYPE html>
<html>
    <head>
       <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Service helps start a worker cooperative by finding partners." />
    <meta name="keywords" content="Worker Cooperative, Start Business, Find Partners, Start Worker Coopertive, Startup, Coop, Democratic Workplace"/>
    <meta name="ROBOTS" content="index, follow"/>
    <meta name="google-site-verification" content="_aPEVkjBNFbwLKmkl7t55JWXaaV5IVwy-TW3Z8Xgf5s" />
    <meta name="msvalidate.01" content="3951BCB20C070743304BB86092B73B27" />
    <meta property="og:title" content="Relevant Cooperation - Start a Worker Cooperative by Finding Partners and Business Concepts." />
    <meta property="og:type" content="website" />
    <meta property="og:url" content="https://relevant-coop.org/" />
    <meta property="og:description" content="Service helps start a worker cooperative by finding partners."/>
    <meta property="og:video" content="https://youtu.be/JVHjg5bXKOc"/>

    <link rel="stylesheet" href="news-assets/css/pure-min.css">
    <link rel="stylesheet" href="news-assets/css/grids-responsive-min.css">
     <link rel="stylesheet" href="news-assets/css/tabs.css">
    <title>Relevant Cooperation -  Start a Worker Cooperative by Finding Partners and Business Concepts.</title>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">

    <!--[if lte IE 8]>
            <link rel="stylesheet" href="css/side-menu-old-ie.css">
        <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="news-assets/css/side-menu.css">
    <!--<![endif]-->
    <link rel="stylesheet" href="news-assets/css/menu.css">
   

    <script type='application/ld+json'> 
        {
          "@context": "http://www.schema.org",
          "@type": "WebSite",
          "name": "Relevant Cooperation",
          "alternateName": "Relevant-Coop",
          "url": "https:://relevant-coop.org/news.jsp",
          "description": "start worker cooperative find partners"
        }
    </script>
    <script src="news-assets/js/tabs.js"></script>
    </head>
    <body>
         <nav role="navigation">
                <div id="menuToggle">     
                  <input type="checkbox" />
                  <span></span>
                  <span></span>
                  <span></span>
                 
                  <ul id="menu">
                        <a href="/"><li class="hover-item">Home</li></a>
                        <a href="/about.html"><li class="hover-item">About</li></a>
                        <a href="https://relevant-coop.org/co/#/"><li class="hover-item">Relevant Coop Service</li></a>
                  </ul>
                </div>
              </nav>
            
    <div id="layout">
          
        <div id="main">
            <div class="header" style="background-image: url('news-assets/img/bg4.png');background-size:cover;color:white;">
                <h1>Relevant Cooperation</h1>
                <h2>Resources for Worker Cooperatives</h2> 
            </div>

            <div class="content">
                <h3 style="text-align: center;">Worker Cooperatives in the News</h3>
                <div id="tabs">
                    <!--<div  class="pure-menu pure-menu-horizontal">-->
                    <div  class="pure-menu">
                        
                        <ul class="pure-menu-list">
                            <li class="pure-menu-item"><a href="#" class="pure-menu-link is-active">Democracy At Work (d@w)</a></li>
                            <li class="pure-menu-item"><a href="#" class="pure-menu-link">U.S. Federation of Worker Cooperatives</a></li>
                            <li class="pure-menu-item"><a href="#" class="pure-menu-link">Democratize the Enterprise</a></li>
                            <li class="pure-menu-item"><a href="#" class="pure-menu-link">Reddit</a></li>
                            <li class="pure-menu-item"><a href="#" class="pure-menu-link">Google News</a></li>                         
                        </ul>
                    </div>
                    <hr/>
                 
                    <div class="c-tab is-active">
                        <div class="c-tab__content">
                             <div>Democracy At Work</div>
                            <% if (DemAtWorkFeedData.getInstance().getNodeList() != null && DemAtWorkFeedData.getInstance().getNodeList().size() > 0) { %>
                            
                                <ul>
                                    <% for (Node node : DemAtWorkFeedData.getInstance().getNodeList()) { %>
                                        <li class="news-item"><a class="news-link" href="<%=node.getLink()%>" target="_blank"><%=node.getTitle()%></a></li>
                                    <% } %>
                               </ul>
                           
                            <% } else { %>
                                <div>News Source not currently available.</div>
                            <% } %>
                   
                        </div>
                    </div>
                    <div class="c-tab">
                        <div class="c-tab__content">
                            <div>U.S. Federation of Worker Cooperatives</div>
                            <% if (UsFedCoopFeedData.getInstance().getNodeList() != null && UsFedCoopFeedData.getInstance().getNodeList().size() > 0) { %>
                            
                                <ul>
                                    <% for (Node node : UsFedCoopFeedData.getInstance().getNodeList()) { %>
                                        <li class="news-item"><a class="news-link" href="<%=node.getLink()%>" target="_blank"><%=node.getTitle()%></a></li>
                                    <% } %>
                               </ul>
                           
                            <% } else { %>
                                <div>News Source not currently available.</div>
                            <% } %>
                        </div>
                    </div>
                    <div class="c-tab">
                        <div class="c-tab__content">
                            <div>Democratize the Enterprise</div>
                            <% if (DemEnterpriseFeedData.getInstance().getNodeList() != null && DemEnterpriseFeedData.getInstance().getNodeList().size() > 0) { %>
                            
                                <ul>
                                    <% for (Node node : DemEnterpriseFeedData.getInstance().getNodeList()) { %>
                                        <li class="news-item"><a class="news-link" href="<%=node.getLink()%>" target="_blank"><%=node.getTitle()%></a></li>
                                    <% } %>
                               </ul>
                           
                            <% } else { %>
                                <div>News Source not currently available.</div>
                            <% } %>
                        </div>
                    </div>
                     <div class="c-tab">
                        <div class="c-tab__content">
                            <div>Reddit</div>
                            <% if (RedditFeedData.getInstance().getNodeList() != null && RedditFeedData.getInstance().getNodeList().size() > 0) { %>
                            
                                <ul>
                                    <% for (Node node : RedditFeedData.getInstance().getNodeList()) { %>
                                        <li class="news-item"><a class="news-link" href="<%=node.getLink()%>" target="_blank"><%=node.getTitle()%></a></li>
                                    <% } %>
                               </ul>
                           
                            <% } else { %>
                                <div>News Source not currently available.</div>
                            <% } %>
                        </div>
                    </div>
                    <div class="c-tab">
                        <div class="c-tab__content">
                            <div>Google News</div>
                            <% if (GoogleFeedData.getInstance().getNodeList() != null && GoogleFeedData.getInstance().getNodeList().size() > 0) { %>
                            
                                <ul>
                                    <% for (Node node : GoogleFeedData.getInstance().getNodeList()) { %>
                                        <li class="news-item"><a class="news-link" href="<%=node.getLink()%>" target="_blank"><%=node.getTitle()%></a></li>
                                    <% } %>
                               </ul>
                           
                            <% } else { %>
                                <div>News Source not currently available.</div>
                            <% } %>
                        </div>
                    </div>
                    
                </div> 
            </div>
        </div>
    </div>
    <script>
            document.onreadystatechange = function () {
                if (document.readyState == "complete") {
                    var myTabs = tabs({
                        el: '#tabs',
                        tabNavigationLinks: '.pure-menu-link',
                        tabContentContainers: '.c-tab'
                    });
                    myTabs.init();
                }
            }
      </script>  
    </body>
</html>
