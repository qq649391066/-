#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include "touch.h"
#include "num_zk.h"
#include "string.h"
#include <sys/socket.h>
#include <pthread.h>
#include <netinet/in.h>
#include <arpa/inet.h>




int *lcd_memory; //记住映射好的空间起始地址
int lcd_fd ;
int socket_fd;   //存储本地网络套接字
int send_voice_fd;

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
	for(j = x; j < x+width; j++)
	{
		for(i = y; i < y+height; i++)
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

//画横线
void draw_line(int x0, int y0, int length, int color)
{
	int i;
	for(i = x0; i < x0+length; i++)
	{
		*(lcd_memory+y0*800+i) = color;
	}
}


//从lcd映射空间指定位置读取图像数据
void read_lcd_memory_data(int x, int y, int width, int height, int *buf)
{
	int i, j;
	int s = 0;
	for(i = 0; i < height; i++)
	{
		for(j = 0; j < width; j++)
		{
			*buf++ = *(lcd_memory+(i+y)*800+j+x);
		}
	}
}


//显示BMP数据流
void lcd_draw_bmp_data(int x, int y, int width, int height, int *buf)
{
	int i, j;
	for(i = 0; i < height; i++)
	{
		for(j = 0; j < width; j++)
		{
			*(lcd_memory+(i+y)*800+j+x) = *buf++;
		}
	}
}


//画竖线
void draw_y(int x0, int y0, int height, int color )
{
	int i;
	for(i = y0; i < y0+height; i++)
	{
		*(lcd_memory+i*800+x0) = color;
	}
}


//显示数字8*16  棣书格式
void show_num8x16(int x,int y, int num, int color)
{
	int i = 0,j = 0, s = 7;
	char A = 0x1;
	char *p = n_buf[num];

	for(i = 0; i < 16; i++)
	{
		s = 0;
		for(j = 0; j < 8; j++)
		{

			if(A << s & n_buf[num][i])
			{
				*(lcd_memory+ (i+y)*800 + (j+x)) = color;
			}
			s++;
		}
	}
}
//显示   .   棣书格式
void show_point(int x,int y, int color)
{
	int i = 0,j = 0, s = 0;
	char A = 0x1;
	int k;

	k = 0;
	for(i = 0; i < 32; i++)
	{
		s = 0;
		for(j = 0; j < 8; j++)
		{
			if(A<<s & _point_[k])
			{
				*(lcd_memory+ (i+y)*800 + (j+x)) = color;
			}
			s++;
		}
		s = 0;
		k++;
		for(j = 8 ; j < 16; j++)
		{
			if(A<<s  & _point_[k])
			{
				*(lcd_memory+ (i+y)*800 + (j+x)) = color;
			}
			s++;
		}
		k++;
	}
}

//显示   #   棣书格式
void show_j(int x,int y, int color)
{
	int i = 0,j = 0, s = 0;
	char A = 0x1;
	int k;

	k = 0;
	for(i = 0; i < 32; i++)
	{
		s = 0;
		for(j = 0; j < 8; j++)
		{
			if(A<<s & _numj[k])
			{
				*(lcd_memory+ (i+y)*800 + (j+x)) = color;
			}
			s++;
		}
		s = 0;
		k++;
		for(j = 8 ; j < 16; j++)
		{
			if(A<<s  & _numj[k])
			{
				*(lcd_memory+ (i+y)*800 + (j+x)) = color;
			}
			s++;
		}
		k++;
	}
}


//显示数字16*32   棣书格式
void show_num16x32(int x,int y, int num, int color)
{
	int i = 0,j = 0, s = 0;
	char A = 0x1;
	int k;

	k = 0;
	for(i = 0; i < 32; i++)
	{
		s = 0;
		for(j = 0; j < 8; j++)
		{
			if(A<<s  & n_16x32[num][k])
			{
				*(lcd_memory+ (i+y)*800 + (j+x)) = color;
			}
			s++;
		}
		s = 0;
		k++;
		for(j = 8 ; j < 16; j++)
		{
			if(A<<s  & n_16x32[num][k])
			{
				*(lcd_memory+ (i+y)*800 + (j+x)) = color;
			}
			s++;
		}
		k++;
	}
}



//画按键框
void draw_kay()
{
	draw_line(40, 40,  240, 0xFF0000);
	draw_line(40, 100, 240, 0xFF0000);
	draw_line(40, 160, 240, 0xFF0000);
	draw_line(40, 220, 240, 0xFF0000);
	draw_line(40, 420, 240, 0xFF0000);

	draw_y(40,  40, 380, 0xFF0000);
	draw_y(100, 40, 180, 0xFF0000);
	draw_y(160, 40, 180, 0xFF0000);
	draw_y(220, 40, 180, 0xFF0000);
	draw_y(280, 40, 380, 0xFF0000);

	show_num16x32(60,  55, 1 ,0xFF0F00);
	show_num16x32(120, 55, 2 ,0xFF0F00);
	show_num16x32(180, 55, 3 ,0xFF0F00);

	show_num16x32(60,  115, 4 ,0xFF0F00);
	show_num16x32(120, 115, 5 ,0xFF0F00);
	show_num16x32(180, 115, 6 ,0xFF0F00);

	show_num16x32(60,  175, 7 ,0xFF0F00);
	show_num16x32(120, 175, 8 ,0xFF0F00);
	show_num16x32(180, 175, 9 ,0xFF0F00);

	show_num16x32(240, 175, 0 ,0xFF0F00);  //显示 0

	show_point(240, 55, 0xFF0F00); //显示 .

	show_j(240,115, 0xFF0F00); //显示 #

	lcd_draw_bmp(60,255 , "bt1.bmp"); //呼叫按钮
}


//显示ip
void show_ip(int x, int y, char *ip)
{
	int i;
	int x_size = x, y_seze = y;
	char card_buf[30] = {0};
	sprintf(card_buf, "%s", ip);
	for(i = 0; i < strlen(card_buf); i++)
	{
		show_num16x32(x_size, y_seze, card_buf[i]-48, 0xff0000); //从左到右显示
		x_size += 20;
	}

}

//接收数据的线程函数
void *recv_server(void *argv)
{
	char tmp_buf[1024] = {0}; //临时存储接收到数据
	int ret = 0;				//存储返回值
	//先新建一个文件，用于存储语音消息
	int voice_fd = open("./voice.txt", O_RDWR|O_CREAT, 0777);
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
			ret = read(client_socket_fd, tmp_buf, 1024);
			if(strstr(tmp_buf, "send_finish"))
			{
				//完成接受
				printf("收到一个语音包\n");
				//播放语音
				close(voice_fd);
				system("aplay recv_voice.wav &");
				sleep(8);
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
int network_connect(char *usr_id)
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
	dest_addr.sin_addr.s_addr=inet_addr(usr_id); //ip号

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
		ret = solve_coord(x,y); //分析触摸屏坐标
		if(ret == 's')
		{

			//录音，录音完毕就发送
			accord_send();
			system("")
		}
		if(ret == 'q')
		{
			//断开网络连接
			close(send_voice_fd);

			printf("服务器断开!!\n");
			return 0;
		}
	}
	return 0;
}


//录音+发送
int accord_send()
{

	int ret = 0;
	int tmp_buf[1024]={0};//临时存储数据的数组
	int file_fd = open("example.wav", O_RDONLY); //打开一个文本
	if(file_fd == -1)
	{
		perror("open example.wav error");
		return -1;
	}

	while(1)
	{

		ret = read(file_fd, tmp_buf, 1024); //从文本中读取理想1024
		printf("读%d\n",ret);
		if(ret == -1 || ret == 0) //读取完数据 ret = 0；
		{
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




//编译命令  arm-linux-gcc interface_design.c touch.c -o touch_bmp
int mem_buf1[80-40][740-390]={0};//存储Ip地址输入框的背景
int mem_buf2[740-390][235-126]={0}; //接收语音提示框的背景

int main()
{
	int x, y;
	int i;
	char ret;
	char usr_id[15]={0};
	open_touch(); //打开触摸

	open_lcd();//打开显示屏

	show_color(0, 0, 800, 480, 0);			//把屏幕画成黑色
	show_bar(100, 200, 600, 80, 0xFFFF00);	//画进度条
	lcd_draw_bmp(0,0, "main.bmp");//画主界面

	draw_kay();	//画键盘

	read_lcd_memory_data(390, 40,  740-390, 80-40, (int *)mem_buf1);//把背景图的ip地址框数据读取
	read_lcd_memory_data(390, 126, 740-390, 235-126, (int *)mem_buf2);//把背景图的接收语音提示框数据读取

	init_tcp_network(); //初始化网络

	while(1)
	{

		read_touch(&x, &y);		//等待点击触摸屏
		ret = solve_coord(x,y); //分析触摸屏坐标
		i=0;
		if(ret!='h'&&ret!='q'&&ret!='o'&&ret!='s' && ret != 'e')
		{
			lcd_draw_bmp_data(390, 40, 740-390, 80-40, (int *)mem_buf1); //把刚刚读取好的ip框背景图重新刷写一清空框框
			memset(usr_id, 0, 15);
			while(1)
			{
				read_touch(&x, &y);		//等待点击触摸屏
				ret =  (x,y);	//分析触摸屏坐标
				if(ret == 'h') 			//如果按下了呼叫按键，退出键盘，进行网络访问
				{
					//网络呼叫connect
					printf("%s\n", usr_id);
					ret = network_connect(usr_id); //进行网络访问
					if(ret == -1)
					{
						perror("对方不在线");
					}
					break;
				}
				if(ret == '#')			//重新输入
				{
					lcd_draw_bmp_data(390, 40, 740-390, 80-40, (int *)mem_buf1); //把刚刚读取好的ip框背景图重新刷写一清空框框
					memset(usr_id, 0, 15); //清空存储ip信息的数组，以从新输入
					i = 0;
					continue;
				}
				if(ret == 'e' || i >= 15 || ret=='q' || ret=='o' || ret=='s')	//如果按下了其他无效键，就不存储按键值，从新读取坐标 //如果ip地址个数已经超过15个字节，就不在进行任何输入，直到按下退出，就退出去
					continue;

				usr_id[i++] = ret;	 		//把得到的响应的数字存储到ip数组中
				show_ip(390, 50, usr_id);	//显示ip到指定的ip框框

			}
		}

	}

	close_lcd();
	close_touch();
}

//1 把 recv_server   init_tcp_network  network_connect accord_send 四个函数移植你们自己的代码中


 //2 编译命令：arm-linux-gcc interface_design.c touch.c -o project_test -lpthread


3 两个开发网线对接， 每一次重启后开发版后 敲下命令: ifconfig eth0 192.168.1.xxx    （xxx : 接线的两个开发板不能一样例如 ）
//																							  开发板1 ：192.168.1.5
//																 							  开发板2 ：192.168.1.8
 					查询自己的开发板ip命令	：ifconfig
//作业：显示彩虹，从上到下一条一条显示


//  效果，开发板1 或者 2 输入对方ip之后然后按下呼叫，对方会打印 有客户端连接，然后发送方按下按下说话按钮，发送方会打印发送完成
//													 接收方会打印收到一个语音包
//  下载程序方法
//  rz -y 回车
//  选择文件
//  chmod 777 lcd_draw_color
//  ./lcd_draw_color

//  方法2
//	rx lcd_draw_color 回车
//  点击 上方的传输按钮， 选择发送xmoden ，选择文件， 确定
