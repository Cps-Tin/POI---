	/**
     * �����ķ�ʽ��������
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) throws Exception {

        //���ڸ�ʽ������
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //��ȡ����
        List<User> list = userService.getUserList();

        //excel����
        String[] title = {"ID", "����", "�Ա�", "��������"};

        //ʵ��������
        String[] field = {"id", "userName", "gender", "createDate"};

        //sheet��
        String sheetName = "ѧ����Ϣ��";

        //excel�ļ���
        String fileName = sheetName + simpleDateFormat.format(new Date()) + ".xls";

        String[][] content = new String[list.size()][title.length];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            User obj = list.get(i);
            for(int j=0;j<field.length;j++){
                Object object = BeanUtil.getGetMethod(obj,field[j].toString());
                if(object instanceof Date){
                    //�������ڸ�ʽ
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

        //����HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content);

        //��Ӧ���ͻ���
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


	//������Ӧ������
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