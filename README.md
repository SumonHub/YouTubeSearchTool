# YouTubeSearchTool
an easy-to-use tool for using YouTube SearchApi
# Gradle Dependency
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

      allprojects {
          repositories {
            ...
            maven { url 'https://jitpack.io' }
          }
        }
Step 2. Add the dependency
      
      dependencies {
                implementation 'com.github.SumonHub:YouTubeSearchTool:Tag'
        }
        
# Example
Step 1. genarate request url
      
      YouTubeSearchHandler searchHandler = new YouTubeSearchHandler.Builder()
                      .setSearchParam(SEARCH_PARAM)
                      .setSearchType(YouTubeSearchHandler.SEARCH_BY_KEYWORD)
                      .setOrderType(YouTubeSearchHandler.ORDER_BY_DEFAULT)
                      .setApiKey(API_KEY)
                      .setMaxResult(50)
                      .create();
              String myUrl = searchHandler.getUrl();
              
Step 2. to execute search.
 
       searchHandler.execute(myUrl);

Step 3. filally get all search result

      searchHandler.onFinish(new YouTubeSearchHandler.OnTaskCompleted() {

                  @Override
                  public void onTaskCompleted(ArrayList<YouTubeVideoModel> list) {
                    
                  }

                  @Override
                  public void onError() {

                  }

              });
