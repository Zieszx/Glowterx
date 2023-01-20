function checkFileSize() {
    var maxSize = 1048576; // 1 MB
    var file = document.getElementById("profileImage").files[0];
    if (file.size > maxSize) {
        alert("File size is too large. Please select a file smaller than 500KB.");
        document.getElementById("error").innerHTML = "File size too large. Please select a file under 500KB.";
    } else {
        // file size is acceptable, proceed with uploading
    }
}