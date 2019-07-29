var swiper = new Swiper('.swiper-container', {
       loop : true,
      slidesPerView: 1,//页面同时显示的图片数量
      spaceBetween: 10,//页面之间的距离
       autoplay: {
        delay: 1000,//1秒切换一次
         disableOnInteraction: false,//用户操作swiper以后,不停止自动播放
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
    });