document.addEventListener("DOMContentLoaded", function() {
    const logContainer = document.getElementById("logContainer");
    const es = new EventSource("/stream/logs");

    es.addEventListener("log", function(e) {
        const div = document.createElement("div");
        div.className = "log-entry";
        div.textContent = e.data;
        logContainer.prepend(div);
    });

    document.getElementById("taskForm").addEventListener("submit", function(ev) {
        ev.preventDefault();
        const fd = new FormData(ev.target);
        const robotId = fd.get("robotId").trim();
        const type = fd.get("type");
        const payload = JSON.stringify({ type: type });
        const url = robotId
            ? `/api/robots/${encodeURIComponent(robotId)}/tasks`
            : "/api/robots/broadcast";

        fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: payload
        }).then(() => ev.target.reset());
    });
});
