// ... existing code ...
const API_BASE_URL = 'http://192.168.213.128:8080/api';

// 创建axios实例
const api = axios.create({
    baseURL: API_BASE_URL,
    timeout: 10000, // 增加超时时间
    headers: {
        'Content-Type': 'application/json'
    }
});

// 请求拦截器 - 添加调试信息
api.interceptors.request.use(config => {
    console.log(`API请求: ${config.method?.toUpperCase()} ${config.url}`);
    return config;
}, error => {
    console.error('API请求错误:', error);
    return Promise.reject(error);
});

// 响应拦截器 - 统一错误处理
api.interceptors.response.use(response => {
    console.log(`API响应成功: ${response.status} ${response.config.url}`);
    return response;
}, error => {
    console.error('API响应错误:', error);
    return Promise.reject(error);
});

// 测试后端连接
const testConnection = async () => {
    try {
        // 首先尝试/patients端点（这个端点存在）
        const response = await api.get('/patients');
        return { success: true, data: response.data };
    } catch (error) {
        // 如果/patients端点失败，尝试其他端点
        try {
            const response = await api.get('/doctors');
            return { success: true, data: response.data };
        } catch (fallbackError) {
            return { success: false, error: fallbackError.message };
        }
    }
};

// 患者相关API
const getPatientById = async (id) => {
    try {
        const response = await api.get(`/patients/${id}`);
        return { success: true, data: response.data };
    } catch (error) {
        return { success: false, error: error.message };
    }
};

const searchPatientsByName = async (name) => {
    try {
        const response = await api.get(`/patients/search?name=${encodeURIComponent(name)}`);
        return { success: true, data: response.data };
    } catch (error) {
        return { success: false, error: error.message };
    }
};

// 获取患者完整详情（包含就诊记录和检查申请）
const getPatientDetails = async (patientId) => {
    try {
        // 分别获取患者信息、就诊记录和检查申请
        const [patientRes, recordsRes, examsRes] = await Promise.all([
            api.get(`/patients/${patientId}`),
            api.get(`/medical-records/patient/${patientId}`),
            api.get(`/examination-requests/patient/${patientId}`)
        ]);
        
        console.log('分别获取的数据:', {
            patient: patientRes.data,
            records: recordsRes.data,
            exams: examsRes.data
        });
        
        const patientData = {
            ...patientRes.data,
            medicalRecords: recordsRes.data || [],
            examinationRequests: examsRes.data || []
        };
        return { success: true, data: patientData };
    } catch (error) {
        console.error('获取患者详情失败:', error);
        return { success: false, error: error.message };
    }
};

// 就诊记录相关API
const getMedicalRecordsByPatient = async (patientId) => {
    try {
        const response = await api.get(`/medical-records/patient/${patientId}`);
        return { success: true, data: response.data };
    } catch (error) {
        return { success: false, error: error.message };
    }
};

// 检查申请相关API
const getExaminationRequestsByPatient = async (patientId) => {
    try {
        const response = await api.get(`/examination-requests/patient/${patientId}`);
        return { success: true, data: response.data };
    } catch (error) {
        return { success: false, error: error.message };
    }
};

// 医生相关API
const getDoctorById = async (doctorId) => {
    try {
        const response = await api.get(`/doctors/${doctorId}`);
        return { success: true, data: response.data };
    } catch (error) {
        return { success: false, error: error.message };
    }
};

const searchDoctorsByName = async (name) => {
    try {
        const response = await api.get(`/doctors/search?name=${encodeURIComponent(name)}`);
        return { success: true, data: response.data };
    } catch (error) {
        return { success: false, error: error.message };
    }
};

// 获取所有医生列表
const getAllDoctors = async () => {
    try {
        const response = await api.get('/doctors');
        return { success: true, data: response.data };
    } catch (error) {
        return { success: false, error: error.message };
    }
};

window.api = {
    testConnection,
    getPatientById,
    searchPatientsByName,
    getPatientDetails,
    getMedicalRecordsByPatient,
    getExaminationRequestsByPatient,
    getDoctorById,
    searchDoctorsByName,
    getAllDoctors
};

console.log('API函数已成功加载到window.api对象');