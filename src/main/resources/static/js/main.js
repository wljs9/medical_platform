// ... existing code ...
const app = new Vue({
    el: '#app',
    data: {
        searchQuery: '',
        searchType: 'id',
        patients: [],
        selectedPatient: null,
        connectionStatus: '检测中...',
        loading: false,
        errorMessage: '',
        activeTab: 'basic',
        doctors: [] // 存储医生列表
    },
     methods: {
        // 测试后端连接
        async testBackendConnection() {
            try {
                // 检查api对象是否已加载
                if (typeof window.api === 'undefined') {
                    this.connectionStatus = 'API函数未加载，请刷新页面';
                    return;
                }
                
                const result = await window.api.testConnection();
                if (result.success) {
                    this.connectionStatus = '后端连接正常';
                    // 加载医生列表
                    await this.loadDoctors();
                } else {
                    this.connectionStatus = '后端连接失败: ' + result.error;
                }
            } catch (error) {
                this.connectionStatus = '连接检测失败: ' + error.message;
                console.error('连接检测错误:', error);
            }
        },

        // 加载医生列表
        async loadDoctors() {
            try {
                const result = await window.api.getAllDoctors();
                if (result.success) {
                    this.doctors = result.data;
                }
            } catch (error) {
                console.warn('加载医生列表失败:', error);
            }
        },

        // 搜索患者
        async searchPatient() {
            if (!this.searchQuery.trim()) {
                this.errorMessage = '请输入搜索内容';
                return;
            }

            this.loading = true;
            this.errorMessage = '';
            this.patients = [];
            this.selectedPatient = null;

            try {
                let result;
                if (this.searchType === 'id') {
                    const patientId = parseInt(this.searchQuery);
                    if (isNaN(patientId)) {
                        this.errorMessage = '请输入有效的患者ID';
                        this.loading = false;
                        return;
                    }
                    result = await window.api.getPatientDetails(patientId);
                } else {
                    result = await window.api.searchPatientsByName(this.searchQuery);
                }

                if (result.success) {
                    if (this.searchType === 'id') {
                        this.selectedPatient = result.data;
                        this.activeTab = 'basic';
                    } else {
                        this.patients = Array.isArray(result.data) ? result.data : [result.data];
                        if (this.patients.length === 1) {
                            this.selectedPatient = this.patients[0];
                            this.activeTab = 'basic';
                        }
                    }
                } else {
                    this.errorMessage = result.error;
                }
            } catch (error) {
                this.errorMessage = '搜索失败: ' + error.message;
                console.error('搜索错误:', error);
            } finally {
                this.loading = false;
            }
        },

       // 选择患者
        async selectPatient(patient) {
            this.loading = true;
            try {
                // 重新获取患者的完整信息（包含就诊记录和检查申请）
                const result = await window.api.getPatientDetails(patient.patientId);
                if (result.success) {
                    this.selectedPatient = result.data;
                    this.activeTab = 'basic';
                    console.log('患者完整信息已加载:', this.selectedPatient);
                } else {
                    this.errorMessage = '获取患者详情失败: ' + result.error;
                }
            } catch (error) {
                this.errorMessage = '选择患者失败: ' + error.message;
                console.error('选择患者错误:', error);
            } finally {
                this.loading = false;
            }
        },

        // 获取医生信息
        getDoctorInfo(doctorId) {
            const doctor = this.doctors.find(d => d.doctorId === doctorId);
            return doctor ? `${doctor.name} (${doctor.title})` : `医生ID: ${doctorId}`;
        },

        // 格式化日期
        formatDate(dateString) {
            return window.utils ? window.utils.formatDate(dateString) : '未知';
        },

        // 显示性别
        displayGender(gender) {
            if (!gender) return '未知';
            return gender === 'M' ? '男' : gender === 'F' ? '女' : gender;
        },

         // 计算年龄
        calculateAge(birthDate) {
            return window.utils ? window.utils.calculateAge(birthDate) : '未知';
        },

        // 获取状态样式类
        getStatusClass(status) {
            return window.utils ? window.utils.getStatusClass(status) : '';
        },

        // 获取唯一的医生ID列表
        getUniqueDoctorIds() {
            if (!this.selectedPatient) return [];
            
            const doctorIds = new Set();
            
            // 从就诊记录中获取医生ID
            if (this.selectedPatient.medicalRecords) {
                this.selectedPatient.medicalRecords.forEach(record => {
                    if (record.doctorId) doctorIds.add(record.doctorId);
                });
            }
            
            // 从检查申请中获取医生ID
            if (this.selectedPatient.examinationRequests) {
                this.selectedPatient.examinationRequests.forEach(exam => {
                    if (exam.doctorId) doctorIds.add(exam.doctorId);
                });
            }
            
            return Array.from(doctorIds);
        },

        // 获取医生负责的就诊记录数量
        getRecordCountByDoctor(doctorId) {
            if (!this.selectedPatient || !this.selectedPatient.medicalRecords) return 0;
            return this.selectedPatient.medicalRecords.filter(record => record.doctorId === doctorId).length;
        },

         // 获取医生负责的检查申请数量
        getExamCountByDoctor(doctorId) {
            if (!this.selectedPatient || !this.selectedPatient.examinationRequests) {
                return 0;
            }
            return this.selectedPatient.examinationRequests.filter(exam => exam.doctorId === doctorId).length;
        },

        // 处理回车键搜索
        handleKeypress(event) {
            if (event.key === 'Enter') {
                this.searchPatient();
            }
        }
    },
    mounted() {
        this.testBackendConnection();
    }
});