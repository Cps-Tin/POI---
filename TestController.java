	/**
     * 以流的方式导出报表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) throws Exception {

        //日期格式化对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //获取数据
        List<User> list = userService.getUserList();

        //excel标题
        String[] title = {"ID", "名称", "性别", "创建日期"};

        //实体类属性
        String[] field = {"id", "userName", "gender", "createDate"};

        //sheet名
        String sheetName = "学生信息表";

        //excel文件名
        String fileName = sheetName + simpleDateFormat.format(new Date()) + ".xls";

        String[][] content = new String[list.size()][title.length];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            User obj = list.get(i);
            for(int j=0;j<field.length;j++){
                Object object = BeanUtil.getGetMethod(obj,field[j].toString());
                if(object instanceof Date){
                    //处理日期格式
                    content[i][j] = simpleDateFormat.format(object);
                }else{
                    content[i][j] = object.toString();
                }
            }

//            content[i][0] = obj.getId().toString();
//            content[i][1] = obj.getUserName();
//            content[i][2] = obj.getGender().toString();
//            content[i][3] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj.getCreateDate());
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content);

        //响应到客户端
        try {
            setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	//发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }