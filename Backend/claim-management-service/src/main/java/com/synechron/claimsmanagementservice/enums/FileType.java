package com.synechron.claimsmanagementservice.enums;

public enum FileType {
    PDF, //Adobe's Portable Document Format used for forms, reports and scanned documents
    DOC,
    DOCX, //Microsoft Word documents are commonly used for textual information, reports and form submissions
    XLS,
    XLSX, //Microsoft Excel files might be used for itemized repair estimates, cost breakdowns, etc...
    PNG,
    JPG,
    JPEG, //Photographs in various forms can be useful for details about the accident scene, damage to the vehicle, etc...
    GIF,
    TXT, //Simple text files may be used for logs, notes or other textual data without the need for formatting
    CSV //Comma-separated values files are useful for exporting/importing data that needs to be read by some kind of spreadsheet application
}
