// 新增就诊记录页面的工具函数
const addRecordUtils = {
    // 显示性别
    displayGender: (gender) => {
        if (!gender) return '未知';
        return gender === 'M' ? '男' : gender === 'F' ? '女' : gender;
    },

    // 计算年龄
    calculateAge: (birthDate) => {
        if (!birthDate) return '未知';
        const birth = new Date(birthDate);
        const today = new Date();
        let age = today.getFullYear() - birth.getFullYear();
        const monthDiff = today.getMonth() - birth.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
            age--;
        }
        return age;
    },

    // 格式化日期为本地时间字符串
    formatDateForInput: (date) => {
        if (!date) return '';
        const d = new Date(date);
        return d.toISOString().slice(0, 16);
    },

    // 获取当前日期时间（用于默认值）
    getCurrentDateTime: () => {
        return new Date().toISOString().slice(0, 16);
    },

    // 表单验证
    validateForm: (formData, patientMode) => {
        const errors = [];

        // 患者信息验证 - 只验证现有患者选择
        if (!formData.selectedPatient) {
            errors.push('请选择患者');
        }

        // 医生信息验证
        if (!formData.selectedDoctorId) {
            errors.push('请选择医生');
        }

        // 就诊信息验证
        if (!formData.symptoms.trim()) {
            errors.push('请输入症状描述');
        }
        if (!formData.diagnosis.trim()) {
            errors.push('请输入诊断结果');
        }
        if (!formData.visitDate) {
            errors.push('请选择就诊日期');
        }

        // 修复：返回正确的验证结果对象
        return {
            isValid: errors.length === 0,
            message: errors.length > 0 ? errors.join(',') : ''
        };
    },

// 重置表单数据 - 移除新建患者相关字段
resetFormData: () => {
    return {
        selectedPatient: null,
        patientSearchQuery: '',
        selectedDoctorId: '',
        symptoms: '',
        diagnosis: '',
        visitDate: addRecordUtils.getCurrentDateTime(),
        needExamination: false,
        examinationType: '',
    };
},

    // 准备就诊记录数据
    prepareMedicalRecordData: (formData, patientId) => {
        return {
            patientId: patientId,
            doctorId: formData.selectedDoctorId,
            symptoms: formData.symptoms,
            diagnosis: formData.diagnosis,
            visitDate: formData.visitDate
        };
    },

    prepareExaminationData: function(formData, patientId) {
    const now = new Date();
    const requestDate = new Date(formData.visitDate);
    
        return {
            patientId: patientId,
            doctorId: formData.selectedDoctorId,
            examinationType: formData.examinationType,
            requestDate: requestDate.toISOString().split('T')[0], // 转换为 yyyy-MM-dd 格式
            status: 'pending',
            createdTime: now.toISOString().replace('T', ' ').substring(0, 19) // 转换为 yyyy-MM-dd HH:mm:ss 格式
        };
    }
}