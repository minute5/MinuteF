package io.choerodon.test.manager.infra.common.utils;
   public class LongUtils {

       private LongUtils() {
       }

       public static boolean isUserId(Long userId){
           return userId != null && ! userId.equals(0L);
   }
}
