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
let countOfExercises = 0;

function generateDiv() {
    var container = document.getElementById("dynamic-inputs");
    var newDiv = document.createElement("div");

    var originalSelect = document.getElementById("selectedExerciseId");
    if (!originalSelect) {
        console.error("Select element not found!");
        return;
    }

    var clonedSelect = originalSelect.cloneNode(true);
    clonedSelect.id += countOfExercises.toString();
    clonedSelect.name = "selectedExerciseId[]";
    clonedSelect.hidden = false;
    clonedSelect.selectedIndex = 0;

    var inputExerciseCount = document.createElement("input");
    inputExerciseCount.type = "number";
    inputExerciseCount.placeholder = "Exercise count";
    inputExerciseCount.name = "exerciseCount[]";
    inputExerciseCount.style.cssText = "width: 15rem; background-color: rgba(200,200,200, 0.3);";

    var inputSets = document.createElement("input");
    inputSets.type = "number";
    inputSets.placeholder = "Sets";
    inputSets.name = "setCount[]";
    inputSets.style.cssText = "width: 15rem; background-color: rgba(200,200,200, 0.3);";

    var removeButton = document.createElement("button");
    removeButton.innerText = "Remove";
    removeButton.type = "button";
    removeButton.onclick = function () {
        container.removeChild(newDiv);
    };

    newDiv.setAttribute("id", "exerciseCountSetDiv" + countOfExercises.toString());
    newDiv.appendChild(clonedSelect);
    newDiv.appendChild(inputExerciseCount);
    newDiv.appendChild(inputSets);
    newDiv.appendChild(removeButton);

    container.appendChild(newDiv);
    countOfExercises++;
}