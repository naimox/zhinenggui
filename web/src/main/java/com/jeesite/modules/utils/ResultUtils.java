package com.jeesite.modules.utils;

import com.jeesite.modules.common.ResultMsg;
import org.springframework.ui.Model;

import java.util.HashMap;

public class ResultUtils {
    public static void successMode(Model model){
        successMode(model, new HashMap<>());
    }

    public static void successMode(Model model, Object data){
        makeMode(model, ResultMsg.SUCCESS_FLAG, ResultMsg.MSG_SUCCESS_ZN, data);
    }

    public static void inputNull(Model model){
        model.addAttribute("flag", ResultMsg.INPUT_NULL);
        model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
        model.addAttribute("data", new HashMap<>());
    }

    public static void makeMode(Model model, int flag, String msg){
        makeMode(model, flag, msg, new HashMap<>());
    }

    public static void makeMode(Model model, int flag, String msg, Object data){
        model.addAttribute("flag", flag);
        model.addAttribute("msg", msg);
        model.addAttribute("data", data);
    }

    public static void noDevice(Model model) {
        model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
        model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
        model.addAttribute("data", new HashMap<>());
    }
}
