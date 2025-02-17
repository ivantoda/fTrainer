function toggleDropdown() {
        var menu = document.getElementById("dropdownContent");
        menu.style.display = (menu.style.display === "block") ? "none" : "block";
        }
    window.onclick = function(event) {
        if (!event.target.matches('.navbar-profile-picture')) {
            var menu = document.getElementById("dropdownContent");
            if (menu.style.display === "block") {
                menu.style.display = "none";
            }
        }
    }