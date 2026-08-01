/* ============================================================
 * StudentManagementSystem - 前端交互逻辑
 * 与后端 Spring Boot API 对接
 * ============================================================ */

// ---------- 全局配置 ----------
let BACKEND_URL = localStorage.getItem('backendUrl') || 'http://localhost:8080';

// ---------- DOM 加载完成后初始化 ----------
document.addEventListener('DOMContentLoaded', () => {
    initNav();
    initSearchTabs();
    initBackendConfig();
    testConnection(true); // 静默检测后端连接
});

// ---------- 导航切换 ----------
function initNav() {
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => {
        item.addEventListener('click', () => {
            const page = item.dataset.page;
            navigateTo(page);
        });
    });
}

function navigateTo(page) {
    // 切换导航高亮
    document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
    const targetNav = document.querySelector(`.nav-item[data-page="${page}"]`);
    if (targetNav) targetNav.classList.add('active');

    // 切换页面
    document.querySelectorAll('.page').forEach(p => p.classList.add('hidden'));
    const targetPage = document.getElementById(`page-${page}`);
    if (targetPage) targetPage.classList.remove('hidden');

    // 更新标题
    const titles = { dashboard: '概览', search: '查询学生', add: '新增学生', delete: '删除学生' };
    document.getElementById('pageTitle').textContent = titles[page] || page;
}

// ---------- 查询 Tab 切换 ----------
function initSearchTabs() {
    const tabBtns = document.querySelectorAll('.tab-btn');
    tabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            tabBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            // 隐藏所有面板
            document.querySelectorAll('.search-panel').forEach(p => p.classList.add('hidden'));

            // 显示目标面板
            const tab = btn.dataset.tab;
            const panel = document.getElementById(`panel-${tab}`);
            if (panel) panel.classList.remove('hidden');
        });
    });
}

// ---------- 后端地址配置 ----------
function initBackendConfig() {
    const urlInput = document.getElementById('backendUrl');
    urlInput.value = BACKEND_URL;

    urlInput.addEventListener('change', () => {
        BACKEND_URL = urlInput.value.trim().replace(/\/$/, '');
        localStorage.setItem('backendUrl', BACKEND_URL);
        showToast('后端地址已更新', 'info');
        testConnection(false);
    });

    document.getElementById('testConnection').addEventListener('click', () => {
        testConnection(false);
    });
}

// ---------- 测试后端连接 ----------
async function testConnection(silent) {
    const dot = document.getElementById('statusDot');
    const text = document.getElementById('statusText');

    dot.className = 'status-dot checking';
    text.textContent = '检测中...';

    try {
        // 尝试调用一个简单接口来检测连通性
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 5000);

        const resp = await fetch(`${BACKEND_URL}/student/find/id/0`, {
            method: 'GET',
            signal: controller.signal
        });

        clearTimeout(timeoutId);

        // 只要能收到响应（即使 404/400）说明后端在线
        dot.className = 'status-dot connected';
        text.textContent = '已连接';
        if (!silent) showToast('✅ 后端连接成功', 'success');
    } catch (err) {
        dot.className = 'status-dot failed';
        text.textContent = '未连接';
        if (!silent) showToast('❌ 无法连接到后端，请检查地址和后端是否启动', 'error');
    }
}

// ---------- API 调用封装 ----------
async function apiCall(url, options = {}) {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), 10000);

    try {
        const resp = await fetch(`${BACKEND_URL}${url}`, {
            headers: { 'Content-Type': 'application/json', ...options.headers },
            signal: controller.signal,
            ...options
        });

        clearTimeout(timeoutId);
        const data = await resp.json().catch(() => null);
        return data;
    } catch (err) {
        clearTimeout(timeoutId);
        if (err.name === 'AbortError') {
            return { success: false, errorMsg: '请求超时，请检查后端服务是否运行', data: null };
        }
        return { success: false, errorMsg: `网络错误: ${err.message}`, data: null };
    }
}

// ---------- 渲染查询结果 ----------
function renderStudentResult(container, response) {
    container.classList.remove('hidden');

    if (!response) {
        container.innerHTML = `
            <div class="result-title error">❌ 请求失败</div>
            <div class="result-data">无法连接到后端服务，请检查后端是否启动</div>
        `;
        container.className = 'result-section result-error';
        return;
    }

    if (response.success && response.data) {
        const s = response.data;
        container.className = 'result-section result-success';
        container.innerHTML = `
            <div class="result-title success">✅ 查询成功</div>
            <div class="student-card">
                <div class="student-field">
                    <span class="field-label">ID</span>
                    <span class="field-value">${s.id ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">学号</span>
                    <span class="field-value">${s.studentNo ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">姓名</span>
                    <span class="field-value">${s.name ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">年龄</span>
                    <span class="field-value">${s.age ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">班级编号</span>
                    <span class="field-value">${s.className ?? '--'}</span>
                </div>
            </div>
        `;
    } else {
        container.className = 'result-section result-error';
        container.innerHTML = `
            <div class="result-title error">❌ 查询失败</div>
            <div class="result-data">${response.errorMsg || '未知错误'}</div>
        `;
    }
}

// ---------- 查询：按 ID ----------
async function searchById() {
    const id = document.getElementById('searchId').value.trim();
    if (!id) { showToast('请输入学生 ID', 'error'); return; }

    const resultContainer = document.getElementById('searchResult');
    resultContainer.innerHTML = '<div class="result-placeholder"><span class="placeholder-icon">⏳</span><p>查询中...</p></div>';
    resultContainer.classList.remove('hidden');

    const response = await apiCall(`/student/find/id/${encodeURIComponent(id)}`);
    renderStudentResult(resultContainer, response);
}

// ---------- 查询：按姓名 ----------
async function searchByName() {
    const name = document.getElementById('searchName').value.trim();
    if (!name) { showToast('请输入学生姓名', 'error'); return; }

    const resultContainer = document.getElementById('searchResult');
    resultContainer.innerHTML = '<div class="result-placeholder"><span class="placeholder-icon">⏳</span><p>查询中...</p></div>';
    resultContainer.classList.remove('hidden');

    const response = await apiCall(`/student/find/name/${encodeURIComponent(name)}`);
    renderStudentResult(resultContainer, response);
}

// ---------- 查询：按学号 ----------
async function searchByNo() {
    const no = document.getElementById('searchNo').value.trim();
    if (!no) { showToast('请输入学号', 'error'); return; }

    const resultContainer = document.getElementById('searchResult');
    resultContainer.innerHTML = '<div class="result-placeholder"><span class="placeholder-icon">⏳</span><p>查询中...</p></div>';
    resultContainer.classList.remove('hidden');

    const response = await apiCall(`/student/find/no/${encodeURIComponent(no)}`);
    renderStudentResult(resultContainer, response);
}

// ---------- 新增学生 ----------
async function handleAddStudent(event) {
    event.preventDefault();

    const studentNo = document.getElementById('addStudentNo').value.trim();
    const name = document.getElementById('addName').value.trim();
    const age = parseInt(document.getElementById('addAge').value);
    const className = parseInt(document.getElementById('addClassName').value);

    const payload = { studentNo, name, age, className };

    const resultContainer = document.getElementById('addResult');
    resultContainer.classList.remove('hidden');
    resultContainer.innerHTML = '<div class="result-placeholder"><span class="placeholder-icon">⏳</span><p>提交中...</p></div>';

    const response = await apiCall('/student/add', {
        method: 'POST',
        body: JSON.stringify(payload)
    });

    if (response && response.success) {
        resultContainer.className = 'result-section result-success';
        const s = response.data;
        resultContainer.innerHTML = `
            <div class="result-title success">✅ 新增成功</div>
            <div class="student-card">
                <div class="student-field">
                    <span class="field-label">ID</span>
                    <span class="field-value">${s.id ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">学号</span>
                    <span class="field-value">${s.studentNo ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">姓名</span>
                    <span class="field-value">${s.name ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">年龄</span>
                    <span class="field-value">${s.age ?? '--'}</span>
                </div>
                <div class="student-field">
                    <span class="field-label">班级编号</span>
                    <span class="field-value">${s.className ?? '--'}</span>
                </div>
            </div>
        `;
        showToast('学生新增成功！', 'success');
        document.getElementById('addStudentForm').reset();
    } else {
        resultContainer.className = 'result-section result-error';
        resultContainer.innerHTML = `
            <div class="result-title error">❌ 新增失败</div>
            <div class="result-data">${response?.errorMsg || '未知错误'}</div>
        `;
        showToast(response?.errorMsg || '新增失败', 'error');
    }

    return false;
}

// ---------- 删除学生 ----------
async function handleDeleteStudent(event) {
    event.preventDefault();

    const id = document.getElementById('deleteId').value.trim();
    if (!id) { showToast('请输入学生 ID', 'error'); return false; }

    // 二次确认
    if (!confirm(`确定要删除 ID 为 ${id} 的学生吗？此操作不可恢复！`)) {
        return false;
    }

    const resultContainer = document.getElementById('deleteResult');
    resultContainer.classList.remove('hidden');
    resultContainer.innerHTML = '<div class="result-placeholder"><span class="placeholder-icon">⏳</span><p>删除中...</p></div>';

    const response = await apiCall(`/student/delete/id/${encodeURIComponent(id)}`, {
        method: 'DELETE'
    });

    if (response && response.success) {
        resultContainer.className = 'result-section result-success';
        resultContainer.innerHTML = `
            <div class="result-title success">✅ 删除成功</div>
            <div class="result-data">ID 为 ${id} 的学生已成功删除</div>
        `;
        showToast('学生删除成功！', 'success');
        document.getElementById('deleteStudentForm').reset();
    } else {
        resultContainer.className = 'result-section result-error';
        resultContainer.innerHTML = `
            <div class="result-title error">❌ 删除失败</div>
            <div class="result-data">${response?.errorMsg || '未知错误'}</div>
        `;
        showToast(response?.errorMsg || '删除失败', 'error');
    }

    return false;
}

// ---------- Toast 通知 ----------
function showToast(message, type = 'info') {
    const container = document.getElementById('toastContainer');
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;

    container.appendChild(toast);

    setTimeout(() => {
        toast.classList.add('fade-out');
        setTimeout(() => toast.remove(), 300);
    }, 3500);
}

// ---------- 回车键支持 ----------
document.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
        const activePage = document.querySelector('.page:not(.hidden)');
        if (!activePage) return;

        if (activePage.id === 'page-search') {
            const activeTab = document.querySelector('.tab-btn.active')?.dataset.tab;
            if (activeTab === 'byId') searchById();
            else if (activeTab === 'byName') searchByName();
            else if (activeTab === 'byNo') searchByNo();
        }
    }
});
