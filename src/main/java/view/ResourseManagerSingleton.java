package main.java.view;

import java.io.IOException;


public class ResourseManagerSingleton {
      private static ResourseManager languageResourses =null;  
      
      private ResourseManagerSingleton() {
      }
      
      public static ResourseManager getInstance() throws IOException {
    	  if(languageResourses==null) {
    		  languageResourses=new ResourseManager();
    	  }
    	  return languageResourses;
      }
}
