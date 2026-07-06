// 新增就诊记录页面的独立API配置
const ADD_RECORD_API_BASE_URL = 'http://192.168.213.128:8080/api';

const addRecordApi = {
    // 测试连接
    testConnection: async () => {
        try {
            const response = await axios.get(`${ADD_RECORD_API_BASE_URL}/patients`);
            return { success: true, data: response.data };
        } catch (error) {
            return { success: false, error: error.message };
        }
    },

            // 搜索患者 - 支持按ID和姓名搜索
    searchPatients: async (query) => {
        try {
            const searchQuery = query.trim();
            if (!searchQuery) {
                return { success: true, data: [] };
            }
            
            // 判断是ID还是姓名搜索
            const isIdSearch = /^\d+$/.test(searchQuery);
            
            if (isIdSearch) {
                // 按ID搜索 - 直接获取患者详情
                const response = await axios.get(`${ADD_RECORD_API_BASE_URL}/patients/${searchQuery}`);
                return { 
                    success: true, 
                    data: response.data ? [response.data] : [] 
                };
            } else {
                // 按姓名搜索
                const response = await axios.get(`${ADD_RECORD_API_BASE_URL}/patients/search?name=${encodeURIComponent(searchQuery)}`);
                let data = response.data;
                if (!Array.isArray(data)) {
                    data = data ? [data] : [];
                }
                return { success: true, data: data };
            }
        } catch (error) {
            // 如果是按ID搜索但没找到患者，返回空数组而不是错误
            if (error.response && error.response.status === 404) {
                return { success: true, data: [] };
            }
            console.error('搜索患者失败:', error);
            return { success: false, error: error.message };
        }
    },

     // 创建患者
    createPatient: async (patientData) => {
        try {
            // 验证idCard格式 - 必须是10位字符
            if (!patientData.idCard || patientData.idCard.trim().length !== 10) {
                return {
                    success: false,
                    error: 'idCard必须是10位字符'
                };
            }
            
            // 验证联系电话格式
            if (patientData.phone) {
                const phoneRegex = /^[0-9]{11}$/;
                if (!phoneRegex.test(patientData.phone)) {
                    return {
                        success: false,
                        error: '联系电话必须是11位数字'
                    };
                }
            }
            
            const response = await axios.post(`${ADD_RECORD_API_BASE_URL}/patients`, {
                name: patientData.name,
                gender: patientData.gender,
                birthDate: patientData.birthDate,
                phone: patientData.phone,
                idCard: patientData.idCard  // 添加idCard字段
            });
            
            return {
                success: true,
                data: response.data
            };
        } catch (error) {
            console.error('创建患者失败:', error);
            return {
                success: false,
                error: error.response?.data?.message || error.message
            };
        }
    },

    // 获取所有医生
    getAllDoctors: async () => {
        try {
            const response = await axios.get(`${ADD_RECORD_API_BASE_URL}/doctors`);
            return { success: true, data: response.data };
        } catch (error) {
            return { success: false, error: error.message };
        }
    },

    // 获取所有科室
    getAllDepartments: async () => {
        try {
            const response = await axios.get(`${ADD_RECORD_API_BASE_URL}/departments`);
            return { success: true, data: response.data };
        } catch (error) {
            return { success: false, error: error.message };
        }
    },

    // 提交就诊记录
    submitMedicalRecord: async (recordData) => {
        try {
            const response = await axios.post(`${ADD_RECORD_API_BASE_URL}/medical-records`, recordData);
            return { success: true, data: response.data };
        } catch (error) {
            return { success: false, error: error.message };
        }
    },

    // 提交检查申请
    submitExaminationRequest: async function(examinationData) {
        try {
            console.log('提交检查申请数据:', examinationData);
            const response = await axios.post(`${ADD_RECORD_API_BASE_URL}/examination-requests`, examinationData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log('检查申请提交结果:', response.data);
            return { success: true, data: response.data };
        } catch (error) {
            console.error('提交检查申请失败:', error);
            return { 
                success: false, 
                error: error.response?.data || error.message 
            };
        }
    },
};
