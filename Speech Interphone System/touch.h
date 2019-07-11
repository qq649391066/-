#ifndef TOUCH_H
#define TOUCH_H

#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <linux/input.h>


//触摸屏设备文件在  "/dev/input/event0"

//打开显示屏
int open_touch();

//读取触摸数据
void read_touch(int *ts_x, int *ts_y);


//关闭触摸屏
void close_touch();


#endif //TOUCH_H
