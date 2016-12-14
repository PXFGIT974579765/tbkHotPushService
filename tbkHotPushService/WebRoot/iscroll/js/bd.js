/**
 * Created by Administrator on 2016/8/19.
 */
function attachEvent(src,cd){
    //解绑事件
    $(src).unbind();
    //检查是否是触屏设备
    var isTouchDevice="ontouchstart"in window||navigator.msMaxTouchPoints;
    if(isTouchDevice){
        $(src).bind("touchstart",function(event){
            $(this).data("touchon",true);
            //$(this).addClass("pressed")
        });
        $(src).bind("touchend",function(){
            if($(this).data("touchon")){
                cd.bind(this)();
            }
            $(this).data("touchon",false);
        });

        $(src).bind("touchmove",function(){
            $(this).data("touchon",false);
            //$(this).removeClass("pressed")
        })
    }else{
        $(src).bind("mousedown",function(){
            //$(this).addClass("pressed");
            $(this).data("touchon",true);
        });
        $(src).bind("mouseup",function(){
            //$(this).removeClass("pressed");
            $(this).data("touchon",false);
            cd.bind(this)();
        });
    }
}