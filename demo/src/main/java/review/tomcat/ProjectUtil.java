package review.tomcat;

import javax.servlet.Servlet;
import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class ProjectUtil {
    public void load(){

            String webapps = "d:\\tomcat-study\\";
        //0.  war 部署之前自动解压
        //1. 解析项目：遍历文件夹，每个子文件夹当成一个项目

        File[] projects = new File(webapps).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                //文件下的子文件是目录，还是文件==》筛选文件
                return file.isDirectory();
            }
        });

        //2. 解析项目的内容
        for (File project : projects) {
            // 解析xml内容，加载到一个java对象

        }


    }


    /** 配置信息 */
    public class WebXml{
        public String projectPath = null;

        /** servlet定义 */
        Map<String,Object> servlets = new HashMap<>();

        /** Servlet-url 映射*/
        Map<String,String> servletMapping = new HashMap<>();

        // servlet实例信息
        Map<String,Servlet> servletInstance = new HashMap<>();


        // .class文件 -->jvm
        public void loadServlet() throws Exception{
            //定义class 类加载工具，告诉jvm，类在何方
            URL servletURL = new URL("file:"+projectPath+"\\WEB-INF\\class");
            URLClassLoader loader = new URLClassLoader(new URL[]{servletURL});

            // 创建 servlet 对象，提供给我们的tomcat 去调用
            for (Map.Entry<String,Object> entry:servlets.entrySet()){
                String servletName = entry.getKey().toString();
                String servletClassName = entry.getValue().toString();

                // 加载jvm
                Class<?> servletClass = loader.loadClass(servletName);
                // 创建对象
                Servlet servlet = (Servlet)servletClass.newInstance();
                // 保存servlet 实例
                servletInstance.putIfAbsent("servlet",servlet);
            }
        }
    }

}
