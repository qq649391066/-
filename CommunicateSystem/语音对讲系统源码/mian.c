#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include <linux/input.h>
#include <string.h>
#include <stdlib.h>
#include <sys/ioctl.h>
#include <sys/socket.h>
#include <pthread.h>
#include <netinet/in.h>
#include <arpa/inet.h>
int *lcd_memory; //记住映射好的空间起始地址
int lcd_fd ;
int touch_fd;
int socket_fd;   //存储本地网络套接字
int send_voice_fd;

//打开显示屏
int open_touch()
{
	touch_fd = open("/dev/input/event0", O_RDWR);
	if(touch_fd == 1)
	{
		perror("open touch error");
		return -1;
	}
	return 0;
}

//读取触摸数据
void read_touch(int *ts_x, int *ts_y)
{
	int pressure, x, y; //临时存压力值
	struct input_event events; //存储事件报告的结构体
	while(1)
	{
		read(touch_fd, &events, sizeof(events)); //read 阻塞等待，如果没有读到数据，就会一直停在这里
		//分析报告
		if(events.type == EV_ABS)
		{
			if(events.code == ABS_X)
				x = events.value;
			if(events.code == ABS_Y)
				y = events.value;
			if(events.code == ABS_PRESSURE)
			{
				pressure = events.value;
				if(pressure == 0)	//如果松开手压力就会变成 0 代表一次完整的点击
				{
					*ts_x = x;
					*ts_y = y;
					printf("x=%d,y=%d\n", *ts_x, *ts_y);
					return ;
				}
			}
		}

	}
}

//打开显示屏
int open_lcd()
{
	lcd_fd = open("/dev/fb0", O_RDWR);
	if(lcd_fd == 1)
	{
		perror("open lcd error");
		return -1;
	}
	//映射空间
	lcd_memory = mmap(NULL,					//NULL函数自动找合适空间起始地址
					  800*480*4, 			//和LCD显存一样大
					  PROT_READ|PROT_WRITE, //权限可读可写
					  MAP_SHARED,			//内存作用 共享内存
					  lcd_fd, 				//lcd描述符（入口）
					  0);					//偏移量 0

}

//在特定的位置显示特定颜色
void show_color(int x, int y, int width, int height, int color)
{
	int i,j ;
	for(i = y; i < y+height; i++)
	{
		for(j = x; j < x+width; j++)
		{
			*(lcd_memory+i*800+j) = color;
		}
	}
}

//关闭显示屏
void close_lcd()
{
	//取消映射
	munmap(lcd_memory, 800*480*4);
	close(lcd_fd);
}


//在特定的位置显示进度条
void show_bar(int x, int y, int width, int height, int color)
{
	int i,j ;
	for(i = y; i < y+height; i++)
	{
		for(j = x; j < x+width; j++)
		{
			*(lcd_memory+i*800+j) = color;
		}
		usleep(3000); //延时3000us    , sleep(1) 延时1秒
	}
}
//显示一张bmp图
int lcd_draw_bmp(int x, int y, char *bmpname)
{
	int bmp_fd;
	int i, j;
	char red, blue, green;
	int width, height;
	int color;
	char bmp_info[54] = {0};		//存储属性信息
	char bmp_data[800*480*4] = {0}; //存储颜色数据信息
	char *p_data = bmp_data;
	//2打开图片
	bmp_fd = open(bmpname, O_RDONLY);
	//3读取图片数据
	read(bmp_fd, bmp_info, 54); //前54个字节是属性信息

	width  = bmp_info[18] | bmp_info[19]<<8;
	height = bmp_info[22] | bmp_info[23]<<8;
	printf("width=%d\nheight=%d\n", width, height);

	read(bmp_fd, bmp_data, 800*480*4);

	//4整理数据
	for(i=y+height-1; i>=y; i--)
	{
		for(j=x; j<x+width; j++)
		{
			blue = *p_data++;
			green= *p_data++;
			red  = *p_data++;
			color= blue | green << 8 | red << 16;
			//把数据写到映射空间
			*(lcd_memory+i*800+j) = color;
		}
	}

	//6关闭图片
	close(bmp_fd);
}
int lcd_draw_jdt(int x, int y, char *bmpname)
{
	int bmp_fd;
	int i, j;
	char red, blue, green;
	int width, height;
	int color;
	char bmp_info[54] = {0};		//存储属性信息
	char bmp_data[800*480*4] = {0}; //存储颜色数据信息
	char *p_data = bmp_data;
	//2打开图片
	bmp_fd = open(bmpname, O_RDONLY);
	//3读取图片数据
	read(bmp_fd, bmp_info, 54); //前54个字节是属性信息

	width  = bmp_info[18] | bmp_info[19]<<8;
	height = bmp_info[22] | bmp_info[23]<<8;
	printf("width=%d\nheight=%d\n", width, height);

	read(bmp_fd, bmp_data, 800*480*4);

	//4整理数据
	for(i=y+height-1; i>=y; i--)
	{
		for(j=x; j<x+width; j++)
		{
			blue = *p_data++;
			green= *p_data++;
			red  = *p_data++;
			color= blue | green << 8 | red << 16;
			//把数据写到映射空间
			*(lcd_memory+i*800+j) = color;

		}
		usleep(1444);

	}

	//6关闭图片
	close(bmp_fd);
}
int lcd_draw_zifu(int x, int y, char *bmpname)
{
	int bmp_fd;
	int i, j;
	char red, blue, green;
	int width, height;
	int color;
	char bmp_info[54] = {0};		//存储属性信息
	char bmp_data[800*480*4] = {0}; //存储颜色数据信息
	char *p_data = bmp_data;
	//2打开图片
	bmp_fd = open(bmpname, O_RDONLY);
	//3读取图片数据
	read(bmp_fd, bmp_info, 54); //前54个字节是属性信息

	width  = bmp_info[18] | bmp_info[19]<<8;
	height = bmp_info[22] | bmp_info[23]<<8;
	printf("width=%d\nheight=%d\n", width, height);

	read(bmp_fd, bmp_data, 800*480*4);

	//4整理数据
	for(i=y+height-1; i>=y; i--)
	{
		for(j=x; j<x+width; j++)
		{
			blue = *p_data++;
			green= *p_data++;
			red  = *p_data++;
			color= blue | green << 8 | red << 16;
			//把数据写到映射空间
			*(lcd_memory+i*800+j) = color;
		}
	}

	//6关闭图片
	close(bmp_fd);
}
//接收数据的线程函数
void *recv_server(void *argv)
{
	char tmp_buf[1024] = {0}; //临时存储接收到数据
	int ret = 0;				//存储返回值
	//先新建一个文件，用于存储语音消息
	system("rm recv_voice.wav");
	int voice_fd = open("./recv_voice.wav", O_RDWR|O_CREAT, 0777);
	if(voice_fd == -1)
	{
		return (void *)-1;
	}

	struct sockaddr_in client_addr; //结构体存储客户端的ip信息
	socklen_t len = sizeof(client_addr);
	int client_socket_fd;
	//等待客户端连接，接受连接
	while(1)
	{
		printf("正在等待客户端连接\n");
		memset(&client_addr, 0, sizeof(client_addr));
		client_socket_fd = accept(socket_fd, (struct sockaddr *)&client_addr, &len);//等待接受客户端，阻塞等待，停止这一句代码
		printf("有客户端连接\n");
		while(1)
		{
            memset(tmp_buf, 0, 1024);
			ret = read(client_socket_fd, tmp_buf, 1024);
			if(strstr(tmp_buf, "send_finish"))
			{
				//完成接受
				printf("收到一个语音包\n");
				//播放语音
				close(voice_fd);
				system("aplay recv_voice.wav");
				system("rm recv_voice.wav");
				voice_fd = open("./recv_voice.wav", O_RDWR|O_CREAT, 0777);
                continue;

			}
			if(ret == -1 || ret == 0) //客户端掉线
			{
				close(voice_fd);
				break;
			}
			write(voice_fd, tmp_buf, ret);

		}
	}
}

//网络初始化
int init_tcp_network()
{
	//1、创建套接字
	socket_fd = socket(AF_INET, SOCK_STREAM, 0);
	if(socket_fd == -1)
	{
		perror("socket error");
		return -1;
	}

	struct sockaddr_in addr;
	memset(&addr, 0, sizeof(addr));
	addr.sin_family = AF_INET; //ipv4家族协议
	addr.sin_port   =htons(7788);
	addr.sin_addr.s_addr=htonl(INADDR_ANY);
	bind(socket_fd, (struct sockaddr *)&addr, sizeof(addr));//绑定本地ip地址

	listen(socket_fd, 5);//监听网络

	pthread_t id;
    pthread_create(&id, NULL,recv_server, NULL);
    pthread_detach(id);
	printf("创建服务器成功\n");
}

//访问其他服器
int network_connect(char *sz)
{
	int x, y;
	//1、创建套接字
	send_voice_fd = socket(AF_INET, SOCK_STREAM, 0);
	if(socket_fd == -1)
	{
		perror("socket error");
		return -1;
	}
	int ret = 0;

	struct sockaddr_in dest_addr;	//存储目标ip地址的结构
	memset(&dest_addr, 0, sizeof(dest_addr));
	dest_addr.sin_family = AF_INET; //ipv4家族协议
	dest_addr.sin_port   =htons(7788);
	dest_addr.sin_addr.s_addr=inet_addr(sz); //ip号

	ret = connect(send_voice_fd, (struct sockaddr *)&dest_addr,sizeof(dest_addr)); //根据目标地址去连接服务器
	if(ret == -1)
	{
		perror("connect error"); //如果连接失败，退出去
		return -1;
	}
	printf("连接服务器成功\n");
	while(1)
	{
		read_touch(&x, &y);		//等待点击触摸屏
		//ret = solve_coord(x,y); //分析触摸屏坐标
		/*if(x<=200&&x>=110&&y<=300&&y>=260)
		{

			//录音，录音完毕就发送
			accord_send();
			continue;
		}*/
		if(x<=200&&x>=110&&y<=350&&y>=310)
		{
			//断开网络连接
			close(send_voice_fd);
			//memset(sz,0,15);

          lcd_draw_jdt(400,23,"jdt.bmp");
			printf("服务器断开\n");
			break;
		}
		else if(x>580&&x<660&&y>210&&y<360&& ret == 0){
            printf("luyin");

            accord_send();
		}else if(x>580&&x<660&&y>210&&y<360&& ret != 0){
		    printf("没人连接");
		    break;
		}
	}
    return main();
}

void *do_thread(void *arg){
while(1)
    {
    lcd_draw_jdt(400,23,"jdt.bmp");
    show_bar(402,25,10,180,0xffff00);
    usleep(1000*500);
    break;
}}
//录音+发送
int accord_send()
{

	int ret = 0;
	int tmp_buf[1024]={0};//临时存储数据的数组

	system("arecord -d7 -c1 -r16000 -twav -fS16_LE send_voice.wav");
	printf("正在录音");
	int file_fd = open("send_voice.wav", O_RDONLY); //打开一个文本
	if(file_fd == -1)
	{
		perror("open send_voice.wav error");
		return -1;
	}
    pthread_t tid;
    int ret1 = 0;
    //创建一条线程
    ret =pthread_create(&tid, NULL,do_thread,NULL);
    if(ret1 !=0){
        fprintf(stderr,"创建线程失败\n");
        return -1;
    }
    //当线程结束时自动释放id号
    ret1 = pthread_detach(tid);
	while(1)
	{

		ret = read(file_fd, tmp_buf, 1024); //从文本中读取理想1024
		printf("读%d\n",ret);
		if(ret == -1 || ret == 0) //读取完数据 ret = 0；
		{
		    sleep(2);
			//发送结束标志给服务器
			write(send_voice_fd, "send_finish", strlen("send_finish"));
			printf("发送完成\n");
			close(file_fd);
			break;
		}
		ret = write(send_voice_fd, tmp_buf, ret); //每一次读到的数据写到网络套接字中，自动通过网络发送出去
		printf("写%d\n",ret);
		if(ret == -1)
		{
			perror("write to server error");
			return -1;
		}

	}
}



void close_touch()
{
	close(touch_fd);
}
//编译命令  arm-linux-gcc anywhere_bmp.c touch.c -o touch_bmp

int main()
{
	int x, y,i,t,j;
	char sz[15]={0};
	int dia_x[15] = {460,475,490,505,520,535,550,565,580,595,610,625,640,655,670};
	int dia_y = 60;
	open_touch(); //打开触摸
	//打开显示屏
	open_lcd();
	//pthread_t thread;
	//pthread_create(&thread,NULL,do_thread,NULL);

	lcd_draw_bmp(0,0,"bg.bmp");
	lcd_draw_zifu(443,23,"jin.bmp");
	t = 0;
	j = 0;
	i = 0;
	memset(sz,0,15);
	 init_tcp_network();//启动网络

	while(1)
	{
		read_touch(&x, &y);

		if(x > 400 && x < 525 &&y>160&&y<400)
		{
			printf("opening the door");
			//高260-390 宽400-520
			lcd_draw_bmp(380,250,"opendoor1.bmp");
			usleep(430*1000);
			lcd_draw_bmp(380,250,"opendoor2.bmp");
			usleep(430*1000);
			lcd_draw_bmp(380,250,"opendoor3.bmp");
			usleep(430*1000);
			lcd_draw_bmp(380,250,"opendoor4.bmp");
			usleep(430*1000);
		}
		else if(x > 30 && x < 120 &&y>40&&y<90)
		{
			printf("1");
			sz[t] = '1';
			lcd_draw_zifu(dia_x[t],dia_y,"1.bmp");
			t++;
		}
		else if(x > 120 && x < 210 &&y>40&&y<90)
		{
			printf("2");
			sz[t] = '2';
			lcd_draw_zifu(dia_x[t],dia_y,"2.bmp");
			t++;
		}
		else if(x > 210 && x < 300 &&y>40&&y<90)
		{
			printf("3");
			sz[t] = '3';
			lcd_draw_zifu(dia_x[t],dia_y,"3.bmp");
			t++;
		}
		else if(x > 30 && x < 120 &&y>90&&y<140)
		{
			printf("4");
			sz[t] = '4';
			lcd_draw_zifu(dia_x[t],dia_y,"4.bmp");
			t++;
		}
		else if(x > 120 && x < 210 &&y>90&&y<140)
		{
			printf("5");
			sz[t] = '5';
			lcd_draw_zifu(dia_x[t],dia_y,"5.bmp");
			t++;
		}
		else if(x > 210 && x < 300 &&y>90&&y<140)
		{
			printf("6");
			sz[t] = '6';
			lcd_draw_zifu(dia_x[t],dia_y,"6.bmp");
			t++;
		}
		else if(x > 30 && x < 120 &&y>140&&y<190)
		{
			printf("7");
			sz[t] = '7';
			lcd_draw_zifu(dia_x[t],dia_y,"7.bmp");
			t++;
		}
		else if(x > 120 && x < 210 &&y>140&&y<190)
		{
			printf("8");
			sz[t] = '8';
			lcd_draw_zifu(dia_x[t],dia_y,"8.bmp");
			t++;
		}
		else if(x > 210 && x < 300 &&y>140&&y<190)
		{
			printf("9");
			sz[t] = '9';
			lcd_draw_zifu(dia_x[t],dia_y,"9.bmp");
			t++;
		}
		else if(x > 30 && x < 120 &&y>190&&y<240)
		{
			printf("#");
			memset(sz,0,15);
			lcd_draw_zifu(443,23,"jin.bmp");
			t = 0;
		}
		else if(x > 120 && x < 210 &&y>190&&y<240)
		{
			printf("0");
			sz[t] = '0';
			lcd_draw_zifu(dia_x[t],dia_y,"0.bmp");
			t++;
		}
		else if(x > 210 && x < 300 &&y>190&&y<240)
		{
			printf("dou");
			sz[t] = '.';
			lcd_draw_zifu(dia_x[t],dia_y,"dou.bmp");
			t++;
		}
		else if(x > 110 && x < 200 &&y>260&&y<300)
		{

			printf("hujiao%s\n",sz);
			show_bar(402,25,10,180,0xffff00);
			network_connect(sz);
			if(network_connect(sz) == -1){
                perror("对方不在线");
			}
             break;

		}
	/*	else if(x > 110 && x < 200 &&y>310&&y<350)
		{
			printf("guaduan");
            memset(sz,0,15);
         //   lcd_draw_zifu(443,23,"jin.bmp");
			t = 0;
		}*/
		else
		continue;
	}


	close_lcd();
	close_touch();
}



//作业：显示彩虹，从上到下一条一条显示

//  编译命令 arm-linux-gcc lcd_draw_color.c -o lcd_draw_color
//  下载程序方法
//  rz -y 回车
//  选择文件
//  chmod 777 lcd_draw_color
//  ./lcd_draw_color

//  方法2
//	rx lcd_draw_color 回车
//  点击 上方的传输按钮， 选择发送xmoden ，选择文件， 确定
