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
function toggleDiv(divId) {
            var div = document.getElementById(divId);
            if (div.style.display === "none") {
                div.style.display = "block";
            } else {
                div.style.display = "none";
            }
        }