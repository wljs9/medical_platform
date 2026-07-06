package datagen;

import java.sql.*;
import java.util.Calendar;
import java.util.Random;

public class MedicalDataGenerator {
    private static final String[] DEPARTMENTS = {
            "内科", "外科", "妇产科", "儿科", "眼科",
            "耳鼻喉科", "口腔科", "皮肤科", "急诊科", "麻醉科"
    };

    private static final String[] DOCTOR_TITLES = {
            "主任医师", "副主任医师", "主治医师", "住院医师"
    };

    private static final String[] COMMON_SURNAMES = {
            "李", "王", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴"
    };

    private static final String[] COMMON_NAMES = {
            "伟", "芳", "娜", "敏", "静", "丽", "强", "磊", "军", "洋",
            "勇", "艳", "杰", "娟", "涛", "明", "超", "霞", "鹏", "鑫"
    };

    private static final String[] EXAM_TYPES = {
            "血常规", "尿常规", "肝功能", "肾功能", "心电图",
            "B超", "CT", "MRI", "X光", "胃镜"
    };

    private static final String[][] SYMPTOMS_DIAGNOSES = {
            {"发热、咳嗽、咽痛", "上呼吸道感染"},
            {"头痛、头晕", "高血压"},
            {"关节疼痛", "关节炎"},
            {"胸闷、气短", "冠心病"},
            {"腹痛、腹泻", "肠胃炎"},
            {"皮疹、瘙痒", "过敏性皮炎"},
            {"视力模糊", "白内障"},
            {"腰痛", "腰椎间盘突出"},
            {"心悸", "心律失常"},
            {"咳嗽、胸痛", "支气管炎"}
    };

    private static Random random = new Random();
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // 显式加载MySQL驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 数据库连接配置
            String url = "jdbc:mysql://localhost:3306/medical_platform?useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "2019121";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功！");

            // 生成数据
            generateDepartments();      // 生成科室
            generateDoctors(200);       // 生成200个医生
            generatePatients(50000);    // 生成50000个患者
            generateMedicalRecords(100000); // 生成100000条病历
            generateExaminations(80000); // 生成80000条检查申请

            System.out.println("所有数据生成完成！总数据量超过10万条");

        } catch (ClassNotFoundException e) {
            System.err.println("找不到MySQL驱动，请检查依赖配置");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 生成科室数据
    private static void generateDepartments() throws SQLException {
        System.out.println("生成科室数据...");
        String sql = "INSERT INTO department (department_name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (String deptName : DEPARTMENTS) {
                stmt.setString(1, deptName);
                stmt.executeUpdate();
            }
        }
        System.out.println("科室数据生成完成");
    }

    // 生成医生数据（批量插入优化）
    private static void generateDoctors(int count) throws SQLException {
        System.out.println("生成医生数据...");
        String sql = "INSERT INTO doctor (name, gender, department_id, title, phone) VALUES (?, ?, ?, ?, ?)";

        connection.setAutoCommit(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String name = generateChineseName();
                String gender = random.nextBoolean() ? "M" : "F";
                int deptId = random.nextInt(DEPARTMENTS.length) + 1;
                String title = DOCTOR_TITLES[random.nextInt(DOCTOR_TITLES.length)];
                String phone = generatePhoneNumber();

                stmt.setString(1, name);
                stmt.setString(2, gender);
                stmt.setInt(3, deptId);
                stmt.setString(4, title);
                stmt.setString(5, phone);
                stmt.addBatch();

                if (i % 100 == 0) {
                    stmt.executeBatch();
                    connection.commit();
                    System.out.println("已生成 " + i + " 个医生");
                }
            }
            stmt.executeBatch();
            connection.commit();
        }
        connection.setAutoCommit(true);
        System.out.println("医生数据生成完成");
    }

    // 生成患者数据（修复日期问题）
    private static void generatePatients(int count) throws SQLException {
        System.out.println("生成患者数据...");
        String sql = "INSERT INTO patient (name, gender, birth_date, phone, id_card) VALUES (?, ?, ?, ?, ?)";

        connection.setAutoCommit(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String name = generateChineseName();
                String gender = random.nextBoolean() ? "M" : "F";

                // 修复：直接使用java.sql.Date
                java.sql.Date birthDate = generateSqlBirthDate();
                String phone = generatePhoneNumber();
                String idCard = generateIdCard(i);

                stmt.setString(1, name);
                stmt.setString(2, gender);
                stmt.setDate(3, birthDate);  // 直接使用java.sql.Date
                stmt.setString(4, phone);
                stmt.setString(5, idCard);
                stmt.addBatch();

                if (i % 1000 == 0) {
                    stmt.executeBatch();
                    connection.commit();
                    System.out.println("已生成 " + i + " 个患者");
                }
            }
            stmt.executeBatch();
            connection.commit();
        }
        connection.setAutoCommit(true);
        System.out.println("患者数据生成完成");
    }

    // 生成病历数据（修复日期问题）
    private static void generateMedicalRecords(int count) throws SQLException {
        System.out.println("生成病历数据...");
        String sql = "INSERT INTO medical_record (patient_id, doctor_id, visit_date, symptoms, diagnosis) VALUES (?, ?, ?, ?, ?)";

        connection.setAutoCommit(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                int patientId = random.nextInt(50000) + 1;
                int doctorId = random.nextInt(200) + 1;

                // 修复：直接使用java.sql.Date
                java.sql.Date visitDate = generateSqlVisitDate();
                String[] symptomDiagnosis = SYMPTOMS_DIAGNOSES[random.nextInt(SYMPTOMS_DIAGNOSES.length)];

                stmt.setInt(1, patientId);
                stmt.setInt(2, doctorId);
                stmt.setDate(3, visitDate);  // 直接使用java.sql.Date
                stmt.setString(4, symptomDiagnosis[0]);
                stmt.setString(5, symptomDiagnosis[1]);
                stmt.addBatch();

                if (i % 5000 == 0) {
                    stmt.executeBatch();
                    connection.commit();
                    System.out.println("已生成 " + i + " 条病历记录");
                }
            }
            stmt.executeBatch();
            connection.commit();
        }
        connection.setAutoCommit(true);
        System.out.println("病历数据生成完成");
    }

    // 生成检查申请数据（修复日期问题）
    private static void generateExaminations(int count) throws SQLException {
        System.out.println("生成检查申请数据...");
        String sql = "INSERT INTO examination_request (patient_id, doctor_id, request_date, examination_type, status) VALUES (?, ?, ?, ?, ?)";

        connection.setAutoCommit(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                int patientId = random.nextInt(50000) + 1;
                int doctorId = random.nextInt(200) + 1;

                // 修复：直接使用java.sql.Date
                java.sql.Date requestDate = generateSqlVisitDate();
                String examType = EXAM_TYPES[random.nextInt(EXAM_TYPES.length)];
                String status = random.nextDouble() > 0.2 ? "completed" : "pending";

                stmt.setInt(1, patientId);
                stmt.setInt(2, doctorId);
                stmt.setDate(3, requestDate);  // 直接使用java.sql.Date
                stmt.setString(4, examType);
                stmt.setString(5, status);
                stmt.addBatch();

                if (i % 5000 == 0) {
                    stmt.executeBatch();
                    connection.commit();
                    System.out.println("已生成 " + i + " 条检查申请");
                }
            }
            stmt.executeBatch();
            connection.commit();
        }
        connection.setAutoCommit(true);
        System.out.println("检查申请数据生成完成");
    }

    // 辅助方法
    private static String generateChineseName() {
        String surname = COMMON_SURNAMES[random.nextInt(COMMON_SURNAMES.length)];
        String name = COMMON_NAMES[random.nextInt(COMMON_NAMES.length)];
        if (random.nextDouble() < 0.3) {
            name += COMMON_NAMES[random.nextInt(COMMON_NAMES.length)];
        }
        return surname + name;
    }

    private static String generatePhoneNumber() {
        String[] prefixes = {"130", "131", "132", "135", "136", "137", "138", "139",
                "150", "151", "152", "155", "156", "157", "158", "159",
                "180", "181", "182", "183", "185", "186", "187", "188", "189"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static String generateIdCard(int sequence) {
        return String.format("10%08d", sequence);
    }

    // 直接生成java.sql.Date
    private static java.sql.Date generateSqlBirthDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -random.nextInt(62) - 18);
        cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(365));
        return new java.sql.Date(cal.getTimeInMillis());
    }

    // 直接生成java.sql.Date
    private static java.sql.Date generateSqlVisitDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(730));
        return new java.sql.Date(cal.getTimeInMillis());
    }
}