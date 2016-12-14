/**
 * Created by Administrator on 2016/8/19.
 */
$(function(){
    //返回
    attachEvent($("#scrollUp"),function (){
        $('body,html').animate({
            scrollTop: 0
        }, 200);
        return false;
    });
    //返回 end





});