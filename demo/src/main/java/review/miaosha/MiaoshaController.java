package review.miaosha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MiaoshaController {
    @Autowired
    MiaoshaService miaoshaService;
    /** 跳转首页 */
    @RequestMapping("/")
    public String index(ModelMap model, HttpServletRequest request){
        String message = "当前服务窗口："+request.getLocalAddr()+":"+request.getLocalPort();
        model.put("message",message);
        return "home";
    }
    /** 秒杀接口 */
    @RequestMapping("/miaosha")
    @ResponseBody
    public Object index(String goodsCode, String userId){
        // http请求，后台就是一个线程去调用service方法
        return miaoshaService.miaosha(goodsCode,userId);
    }
}
