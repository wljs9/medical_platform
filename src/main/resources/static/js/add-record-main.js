

// 患者选择组件
Vue.component('patient-selection', {
    template: '#patient-selection-template',
    props: ['selectedPatient', 'patientSearchQuery', 'patientSearchResults', 'addRecordUtils'],
    data: function() {
        return {
            localSearchQuery: this.patientSearchQuery
        };
    },
    methods: {
        handleSearchInput() {
            this.$emit('update:patient-search-query', this.localSearchQuery);
        }
    },
    watch: {
        patientSearchQuery(newVal) {
            this.localSearchQuery = newVal;
        }
    }
});

Vue.component('doctor-selection', {
    template: '#doctor-selection-template',
    props: ['departments', 'doctors', 'selectedDepartment', 'selectedDoctorId'],
    data: function() {
        return {
            localSelectedDepartment: this.selectedDepartment,
            localSelectedDoctorId: this.selectedDoctorId
        };
    },
    computed: {
        filteredDoctors() {
            if (!this.localSelectedDepartment) {
                return this.doctors;
            }
            return this.doctors.filter(doctor => doctor.departmentId == this.localSelectedDepartment);
        }
    },
    methods: {
        getDepartmentName(departmentId) {
            const dept = this.departments.find(d => d.departmentId == departmentId);
            return dept ? dept.departmentName : '未知科室';
        }
    },
    watch: {
        selectedDepartment(newVal) {
            this.localSelectedDepartment = newVal;
        },
        selectedDoctorId(newVal) {
            this.localSelectedDoctorId = newVal;
        },
        localSelectedDepartment(newVal) {
            this.$emit('update:selected-department', newVal);
            this.$emit('filter-doctors');
        },
        localSelectedDoctorId(newVal) {
            this.$emit('update:selected-doctor-id', newVal);
        }
    }
});

Vue.component('visit-information', {
    template: '#visit-information-template',
    props: ['symptoms', 'diagnosis', 'visitDate'],
    data: function() {
        return {
            localSymptoms: this.symptoms,
            localDiagnosis: this.diagnosis,
            localVisitDate: this.visitDate
        };
    },
    watch: {
        symptoms(newVal) {
            this.localSymptoms = newVal;
        },
        diagnosis(newVal) {
            this.localDiagnosis = newVal;
        },
        visitDate(newVal) {
            this.localVisitDate = newVal;
        },
        localSymptoms(newVal) {
            this.$emit('update:symptoms', newVal);
        },
        localDiagnosis(newVal) {
            this.$emit('update:diagnosis', newVal);
        },
        localVisitDate(newVal) {
            this.$emit('update:visit-date', newVal);
        }
    }
});

Vue.component('examination-request', {
    template: '#examination-request-template',
    props: ['needExamination', 'examinationType'],
    data: function() {
        return {
            localNeedExamination: this.needExamination,
            localExaminationType: this.examinationType
        };
    },
    watch: {
        needExamination(newVal) {
            this.localNeedExamination = newVal;
        },
        examinationType(newVal) {
            this.localExaminationType = newVal;
        },
        localNeedExamination(newVal) {
            this.$emit('update:need-examination', newVal);
        },
        localExaminationType(newVal) {
            this.$emit('update:examination-type', newVal);
        }
    }
});

// Vue应用
new Vue({
    el: '#addRecordApp',
    data: function() {
        return Object.assign({
            connectionStatus: '检测中...',
            errorMessage: '',
            successMessage: '',
            submitting: false,
            departments: [],
            doctors: [],
            patientSearchResults: [],
            selectedDepartment: '',
            addRecordUtils: addRecordUtils,
            // 添加组件模板中使用的数据字段
            selectedPatient: null,
            patientSearchQuery: '',
            selectedDoctorId: '',
            symptoms: '',
            diagnosis: '',
            visitDate: addRecordUtils.getCurrentDateTime(),
            needExamination: false,
            examinationType: ''
        }, addRecordUtils.resetFormData());
    },
    computed: {
        filteredDoctors() {
            if (!this.selectedDepartment) {
                return this.doctors;
            }
            return this.doctors.filter(doctor => doctor.departmentId == this.selectedDepartment);
        }
    },
    methods: {
        // 测试后端连接
        async testBackendConnection() {
            try {
                const result = await addRecordApi.testConnection();
                if (result.success) {
                    this.connectionStatus = '后端连接正常';
                    await this.loadDoctors();
                    await this.loadDepartments();
                } else {
                    this.connectionStatus = '后端连接失败: ' + result.error;
                }
            } catch (error) {
                this.connectionStatus = '连接检测失败: ' + error.message;
            }
        },

        // 加载医生列表
        async loadDoctors() {
            try {
                const result = await addRecordApi.getAllDoctors();
                if (result.success) {
                    this.doctors = result.data;
                }
            } catch (error) {
                console.warn('加载医生列表失败:', error);
            }
        },

        // 加载科室列表
        async loadDepartments() {
            try {
                const result = await addRecordApi.getAllDepartments();
                if (result.success) {
                    this.departments = result.data;
                }
            } catch (error) {
                console.warn('加载科室列表失败:', error);
            }
        },

        // 搜索患者
        async searchPatients(query) {
            const searchQuery = query || this.patientSearchQuery;
            if (!searchQuery.trim()) {
                this.patientSearchResults = [];
                return;
            }

            try {
                const result = await addRecordApi.searchPatients(searchQuery);
                if (result.success) {
                    this.patientSearchResults = Array.isArray(result.data) ? result.data : [result.data];
                } else {
                    this.patientSearchResults = [];
                }
            } catch (error) {
                this.patientSearchResults = [];
                console.error('搜索患者失败:', error);
            }
        },

        // 选择患者
        selectPatient(patient) {
            this.selectedPatient = patient;
            this.patientSearchResults = [];
            this.patientSearchQuery = patient.name;
        },

        // 获取科室名称
        getDepartmentName(departmentId) {
            const dept = this.departments.find(d => d.departmentId == departmentId);
            return dept ? dept.departmentName : '未知科室';
        },

        // 页面加载时获取最后一个患者idCard
        async mounted() {
            await this.testBackendConnection();
        },


           // 提交就诊记录 - 添加调试信息
        submitMedicalRecord: async function() {
            try {
                this.submitting = true;
                this.errorMessage = '';
                this.successMessage = '';

                // 验证表单
                const validationResult = addRecordUtils.validateForm(this.$data);
                if (!validationResult.isValid) {
                    this.errorMessage = validationResult.message;
                    this.submitting = false;
                    return;
                }

                // 准备就诊记录数据
                const patientId = this.selectedPatient?.patientId;
                if (!patientId) {
                    this.errorMessage = '请先选择患者';
                    this.submitting = false;
                    return;
                }

                const medicalRecordData = addRecordUtils.prepareMedicalRecordData(this.$data, patientId);
                console.log('提交就诊记录数据:', medicalRecordData);

                // 提交就诊记录
                const medicalRecordResult = await addRecordApi.submitMedicalRecord(medicalRecordData);
                console.log('就诊记录提交结果:', medicalRecordResult);

                if (!medicalRecordResult.success) {
                    throw new Error(medicalRecordResult.error);
                }

                // 如果需要检查，提交检查申请
                if (this.needExamination && this.examinationType.trim()) {
                    const examinationData = addRecordUtils.prepareExaminationData(this.$data, patientId);
                    console.log('提交检查申请数据:', examinationData);
                    
                    const examinationResult = await addRecordApi.submitExaminationRequest(examinationData);
                    console.log('检查申请提交结果:', examinationResult);

                    if (!examinationResult.success) {
                        throw new Error(examinationResult.error);
                    }
                }

                this.successMessage = '就诊记录提交成功！';
                this.resetForm();
                
            } catch (error) {
                this.errorMessage = '提交失败: ' + error.message;
                console.error('提交就诊记录失败:', error);
            } finally {
                this.submitting = false;
            }
        },

    // 重置表单
    resetForm() {
        Object.assign(this.$data, addRecordUtils.resetFormData());
        this.selectedDepartment = '';
        this.patientSearchResults = [];
        this.errorMessage = '';
        this.successMessage = '';
        this.loadDoctors();
        this.loadDepartments();
    },

        // 筛选医生
        filterDoctors() {
            this.selectedDoctorId = '';
        }
    },
    mounted() {
        this.testBackendConnection();
    }
});