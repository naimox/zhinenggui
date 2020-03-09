package com.jeesite.modules.utils;

import com.jeesite.modules.zhinenggui.dao.SpringUtil;
import com.jeesite.modules.zhinenggui.entity.ZngCabinets;
import com.jeesite.modules.zhinenggui.service.ZngCabinetsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/9/5 0005.
 */
public class AddNewDeviceUtils {

    ZngCabinetsService zhinengguiCabinetsService = SpringUtil.getApplicationContext().getBean(ZngCabinetsService.class);

    public void add(String deviceId, int count, String plateId, int totalIdStart){
        ZngCabinets cabinets;
        for(int i = 1; i <= count; i++){
            cabinets = new ZngCabinets();
            cabinets.setDeviceId(deviceId);
            cabinets.setPlateId(plateId);
            cabinets.setDoorId(strNum(i));
            cabinets.setTotalDoorId(strNum(totalIdStart+i-1));
            cabinets.setState("2");
            cabinets.setDeviceType("1");
            cabinets.setDoorSize("s");
            cabinets.setStatus("0");
            zhinengguiCabinetsService.save(cabinets);
        }
    }

    public void add(String deviceId, int count, String plateId, int totalIdStart, String doorSize){
        ZngCabinets cabinets;
//        for(int i = 1; i <= count; i++){
            cabinets = new ZngCabinets();
            cabinets.setDeviceId(deviceId);
            cabinets.setPlateId(plateId);
            cabinets.setDoorId(strNum(count));
            cabinets.setTotalDoorId(strNum(totalIdStart));
            cabinets.setState("2");
            cabinets.setDeviceType("1");
            cabinets.setDoorSize(doorSize);
            cabinets.setStatus("0");
            System.out.println(cabinets.toString());
//            zhinengguiCabinetsService.save(cabinets);
//        }

    }

    /**
     *
     * @param my y轴长度
     * @param mx x轴长度
     * @param mSize m大小所在y轴位置
     * @param xSize x大小所在y轴位置
     * @param deviceId 设备id
     * @param scrNum 屏幕起始门id
     * @param nextDoor 屏幕所在门数量
     */
    public void addDoors(int banSize, int my, int mx, Integer mSize[], Integer xSize[],
                         String deviceId,Integer scrNum, Integer nextDoor){
//        int my = 10;
//        int mx = 4;
//		  Integer sSize[] = {3, 4, 5, 6, 7, 8, 9};
//        Integer mSize[] = {1, 2};
//        Integer xSize[] = {10};
//        String deviceId = "00001";
//        Integer scrNum = -1;
//        Integer nextDoor = 0;

        Integer nextNum = 0;
        for (Integer x = 1; x <= mx ; x++){
            for (Integer y = 1; y <= my; y++){
                int tId = (x-1)*my+y;
                if (tId == scrNum){
                    tId+=nextDoor;
                    nextNum = 1;
                }
                String doorSize = "s";
                if (Arrays.asList(mSize).contains(y)){
                    doorSize = "m";
                }else if (Arrays.asList(xSize).contains(y)){
                    doorSize = "x";
                }
                String plateId;
                if (banSize == 12)
                    plateId = String.valueOf(x+nextNum);
                else if (banSize == 24){
                    plateId = String.valueOf((int)Math.ceil(x/2.0)+nextNum);
                }else {
                    return;
                }
                int doorId;
                if (banSize == 24){
                    doorId = y+(x%2==0?10:0);
                }else {
                    doorId = y;
                }
                AddNewDeviceUtils addNewDeviceUtils = new AddNewDeviceUtils();
                addNewDeviceUtils.add(deviceId, doorId, plateId, tId, doorSize);
            }
        }
    }


    /**
     *
     * @param my y轴长度
     * @param mx x轴长度
     * @param mSize m大小所在y轴位置
     * @param xSize x大小所在y轴位置
     * @param deviceId 设备id
     * @param scrNum 屏幕起始门id
     * @param nextDoor 屏幕所在门数量
     */
    public void addDoors2(int banSize, int my, int mx, Integer mSize[], Integer xSize[],
                         String deviceId,Integer scrNum, Integer nextDoor){
//     public static void main(String[] args){
//        int my = 10;
//        int mx = 4;
//		  Integer sSize[] = {3, 4, 5, 6, 7, 8, 9};
//        Integer mSize[] = {1, 2};
//        Integer xSize[] = {10};
//        String deviceId = "00001";
//        Integer scrNum = -1;
//        Integer nextDoor = 0;
        Integer nextNum = 0;
        for (Integer x = 1; x <= mx ; x++){
            for (Integer y = 1; y <= my; y++){
                int tId = (x-1)*my+y;
                if (tId == scrNum){
                    tId+=nextDoor;
                    nextNum = 1;
                }
                String doorSize = "s";
                if (Arrays.asList(mSize).contains(y)){
                    doorSize = "m";
                }else if (Arrays.asList(xSize).contains(y)){
                    doorSize = "x";
                }
                String plateId;
                if (banSize == 12) {
                    if (y>5)
                        plateId = String.valueOf((int)Math.ceil(x/2.0)*2+nextNum);
                    else
                        plateId = String.valueOf((int)Math.ceil(x/2.0)*2-1+nextNum);
                }else if (banSize == 24){
                    plateId = String.valueOf((int)Math.ceil(x/2.0)+nextNum);
                }else {
                    return;
                }
                int doorId;
                if (banSize == 24){
                    doorId = (x-1)%2==0?y*2-1:y*2;
                }else {
                    if (y>5){
                        doorId = (x-1)%2==0?(y-5)*2-1:(y-5)*2;
                    }else {
                        doorId = (x-1)%2==0?y*2-1:y*2;
                    }
                }
                AddNewDeviceUtils addNewDeviceUtils = new AddNewDeviceUtils();
                addNewDeviceUtils.add(deviceId, doorId,
                        plateId,
                        tId, doorSize);
            }
        }
    }

    private String strNum(int num){
        String str;
        if(num < 10){
            str = "0" + num;
        }else {
            str = num + "";
        }
        return str;
    }
}
