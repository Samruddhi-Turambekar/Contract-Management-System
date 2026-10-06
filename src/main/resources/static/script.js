const API = "/api";


function getUserId() {
    return localStorage.getItem("userId");
}


function getUserRole() {
    return localStorage.getItem("userRole");
}


function getUserName() {
    return localStorage.getItem("userName") || "User";
}


function authHeaders() {

    return {
        "Content-Type": "application/json",
        "X-User-Id": getUserId()
    };

}


function logout() {

    localStorage.clear();

    window.location.href = "index.html";

}


function goManager() {

    window.location.href =
        "manager-dashboard.html";

}


function goEmployee() {

    window.location.href =
        "employee-dashboard.html";

}


function protectPage(requiredRole) {

    const userId = getUserId();
    const role = getUserRole();


    // No user logged in

    if (!userId) {

        window.location.href = "index.html";

        return false;
    }


    // Wrong role

    if (requiredRole && role !== requiredRole) {

        if (role === "MANAGER") {

            window.location.href =
                "manager-dashboard.html";

        } else {

            window.location.href =
                "employee-dashboard.html";

        }

        return false;
    }


    return true;
}


function showToast(message, success = true) {

    const toast =
        document.createElement("div");

    toast.className =
        "toast " +
        (success ? "success" : "error");

    toast.innerText = message;

    document.body.appendChild(toast);


    setTimeout(() => {

        toast.classList.add("show");

    }, 50);


    setTimeout(() => {

        toast.classList.remove("show");

        setTimeout(() => {

            toast.remove();

        }, 300);

    }, 2500);

}


function statusBadge(status) {

    const value =
        status || "UNKNOWN";

    return `
        <span class="status ${value
            .toLowerCase()
            .replaceAll(" ", "-")}">

            ${value}

        </span>
    `;
}