package com.bowen.springbootmember.controllers;

import com.bowen.springbootmember.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//有關會員操作的控制器Controller
@Controller

public class MemberController {
    //注入依賴物件DataSource
    @Autowired
    private DataSource dataSource;

    String message = null;
    @RequestMapping("/member/registerform")
    public String registerForm(Model model, Member member){
        //透過注入依賴DataSource生產一個連接物件(連接上資料庫伺服器)
        if(member.getUsername() != null && member.getPassword() != null) {
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                //借助Jdbc進行會員註冊作業
                String sql = "Insert Into member(username,password,email,phone) values(?,?,?,?)";
                //1.透過連接物件取出命令物件 配置新增SQL
                PreparedStatement st = connection.prepareStatement(sql);
                //2.設定參數
                st.setString(1,member.getUsername());
                st.setString(2,member.getPassword());
                st.setString(3,member.getEmail());
                st.setString(4,member.getPhone());

                //3.完成新增作業
                int affect = st.executeUpdate();
                message="註冊成功!!!";

                //4.設定訊息
            } catch (SQLException e) {
                e.printStackTrace();
                //設定狀態訊息
                message="會員發生問題，註冊失敗!";
            }finally {
                if(connection !=null ){
                    //有開啟 進行關閉 將連接收集到Connection Pooling
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            //要進行狀態持續 借助Model進行狀態持續 也就是Thymeleaf template
            model.addAttribute("message",message);
            model.addAttribute("member",member);
        }
        //直接調用表單頁面(註冊表單)
        return "memberregister";
    }
}