// ... existing code ...
// 格式化日期
const formatDate = (dateString) => {
    if (!dateString) return '未知';
    try {
        const date = new Date(dateString);
        if (isNaN(date.getTime())) return '日期格式错误';
        return date.toLocaleDateString('zh-CN');
    } catch (error) {
        return '日期格式错误';
    }
};

// 显示性别
const displayGender = (gender) => {
    if (!gender) return '未知';
    return gender === 'M' ? '男' : gender === 'F' ? '女' : gender;
};

// 计算年龄
const calculateAge = (birthDate) => {
    if (!birthDate) return '未知';
    try {
        const today = new Date();
        const birth = new Date(birthDate);
        if (isNaN(birth.getTime())) return '日期格式错误';
        
        let age = today.getFullYear() - birth.getFullYear();
        const monthDiff = today.getMonth() - birth.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
            age--;
        }
        return age;
    } catch (error) {
        return '计算错误';
    }
};

// 获取状态样式类
const getStatusClass = (status) => {
    if (!status) return '';
    switch (status.toLowerCase()) {
        case 'pending': return 'status-pending';
        case 'completed': return 'status-completed';
        case 'cancelled': return 'status-cancelled';
        default: return '';
    }
};

// 导出到全局对象
window.utils = {
    formatDate,
    displayGender,
    calculateAge,
    getStatusClass
};