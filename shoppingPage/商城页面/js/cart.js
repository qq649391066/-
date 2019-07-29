// 页面准备就绪
$(function(){
	// 通过 $('css选择器') id 可以获取页面上的元素
		// var btns = $('.checkbox');
	
		var obj={
			//获取页面上的所有的按钮
			btns:$('.checkbox'),
			//获取商品里的 input复选框
			btn:$('.btn'),
			// 给所有的按钮绑定点击事件
			allClick:function(ele){
				var _this = this;
				ele.click(function(){
					$(this).toggleClass('active');
					console.log(_this.hasbtn())
					_this.hasbtn();
				})
			},
			// 判断商品按钮是否全部选中
			hasbtn:function(){
				var flag = true;
				this.btn.each(function(i,v){
					if (!v.checked) {
						flag = false;
						// return;
					}
				})
				return flag;
			},
			//入口函数
			init:function(){
				this.allClick(this.btns);
				
			}
		}

		obj.init();
})