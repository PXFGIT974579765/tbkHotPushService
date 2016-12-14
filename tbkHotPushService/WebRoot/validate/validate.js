var Validate = {
	Vesion:'1.0',
	Color:'lightpink',	
	validform:function(formName){
		var ispassed = true;
		var e = formName.elements;
		for(var i = 0; i < e.length; i++){			
			var attrValidate = e[i].getAttribute('title');
			switch(e[i].tagName){
				case "SELECT":
					if(attrValidate != null && attrValidate != '' && (e[i].options.length == 0 || e[i].options[e[i].selectedIndex].value == '-1')){
						e[i].style.backgroundColor = Validate.Color;
						e[i].onblur = function(){Validate.vcancel()}
						e[i].onfocus = function(){Validate.vprompt()}
						ispassed = false;		
					}
					break;
				case "INPUT":
					if(attrValidate != null && attrValidate != '' && e[i].value == ''){
						e[i].style.backgroundColor = Validate.Color;						
						e[i].onblur = function(){Validate.vcancel()}
						if(attrValidate.indexOf('统计时间')==-1){
							e[i].onfocus = function(){Validate.vprompt();}
						}
						ispassed = false;		
					}
					break;
				case "TEXTAREA":
					if(attrValidate != null && attrValidate != '' && e[i].value == ''){
						e[i].style.backgroundColor = Validate.Color;
						e[i].onblur = function(){Validate.vcancel()}
						e[i].onfocus = function(){Validate.vprompt()}
						ispassed = false;		
					}
					break;
			}				
		}
		return ispassed;
	},
	vcancel:function(){
		with(event.srcElement){
			if(style.backgroundColor == Validate.Color && value != ''){
				style.backgroundColor = '';
			}
		}
	},
	vprompt:function(){
		with(event.srcElement){
			if(style.backgroundColor == Validate.Color){
				var cursorLoal = CursorDefault.local();		
				WindowDefault.createPopup((cursorLoal.x-36),(cursorLoal.y-36),'240','22',title)
			}
		}
	}
}

