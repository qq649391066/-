#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <linux/input.h>


//触摸屏设备文件在  "/dev/input/event0"

int touch_fd;

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

//分析坐标位置
char solve_coord(int x,int y)
{
	if(x > 40 && y > 40 && x < 280  && y< 220) //键盘区域
	{
		if(x > 40 && y > 40 && x < 100 && y < 100)
			return '1';

		if(x > 100 && y > 40 && x < 160 && y < 100)
			return '2';

		if(x > 160 && y > 40 && x < 220 && y < 100)
			return '3';

		if(x > 40 && y > 100 && x < 100 && y < 160)
			return '4';

		if(x > 100 && y > 100 && x < 160 && y < 160)
			return '5';

		if(x > 160 && y > 100 && x < 220 && y < 160)
			return '6';

		if(x > 40 && y > 160 && x < 100 && y < 220)
			return '7';

		if(x > 100 && y > 160 && x < 160 && y < 220)
			return '8';

		if(x > 160 && y > 160 && x < 220 && y < 220)
			return '9';

		if(x > 220 && y > 40 && x < 280 && y < 100)
			return '.';

		if(x > 220 && y > 160 && x < 280 && y < 220)
			return '0';

		if(x > 220 && y > 100 && x < 280 && y < 160)
			return '#';

	}
	if(x > 60 && y >255 && x < 260 && y < 300) //呼叫按钮
		return 'h';
	if(x > 60 && y >300 && x < 260 && y < 360) //退出呼叫按钮
		return 'q';
	if(x > 60 && y >360 && x < 260 && y < 415) //开门
		return 'o';
	if(x > 446 && y >386 && x < 658 && y < 428) //呼叫按钮
		return 's';

	return 'e'; //代表error无数据
}







//关闭触摸屏
void close_touch()
{
	close(touch_fd);
}




