package com.common;

import com.common.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.List;

/**
 * @author yangzhiguo  2017/11/9.
 */
public class Demo {


    public static void main(String[] args) throws IOException {
        String path = "D:\\user.xlsx";
        Sheet sheet =ExcelUtils.getExcelSheet(path);

        List<Object> objects = ExcelUtils.xlsDto(path, User.class, 1, ExcelUtils.rowCount(sheet));
        for (Object object : objects) {
            // User user = (User) object;
            // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // System.out.println(simpleDateFormat.format(user.getAge()));
            System.out.println(objects.toString());
        }
    }
}
