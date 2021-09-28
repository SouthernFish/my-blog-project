
 const iconList = ['txt.ico', 'pdf.ico', 'ppt.ico', 'word.ico', 'excel.ico', 'note.ico', 'md.ico']
/**
 * 文件类型匹配图标
 * @param {String} fileName [文件名字]
 */
 export function matchFileIcon(fileName) {
  var suffix = getFileSuffix(fileName)
  var result = ''
  // 文件图标列表
  var index = null
   // txt 文件
  var txtList = ['txt', 'TXT']
  result = txtList.some(function (item) {
    index = iconList.findIndex((val) => val === 'txt.ico') 
    return item === suffix
  })
  if (result) {
    return iconList[index]  
  }

  // pdf 文件
  var pdfList = ['pdf', 'PDF']
  result = pdfList.some(function (item) {
    index = iconList.findIndex((val) => val === 'pdf.ico') 
    return item === suffix
  }) 
  if (result) {
    return iconList[index]  
  }

  // ppt 文件
  var pptList = ['ppt', 'pptx']
  result = pptList.some(function (item) {
    index = iconList.findIndex((val) => val === 'ppt.ico') 
    return item === suffix
  }) 
  if (result) {
    return iconList[index]  
  }

  // word 文件
  var wordList = ['doc', 'docx']
  result = wordList.some(function (item) {
    index = iconList.findIndex((val) => val === 'word.ico') 
    return item === suffix
  })
  if (result) {
    return iconList[index]  
  } 

  // excel 文件
  var excelList = ['xls', 'xlsx']
  result = excelList.some(function (item) {
    index = iconList.findIndex((val) => val === 'excel.ico') 
    return item === suffix
  }) 
  if (result) {
    return iconList[index]  
  }

  // note 文件
  var noteList = ['note']
  result = noteList.some(function (item) {
    index = iconList.findIndex((val) => val === 'note.ico') 
    return item === suffix
  })
  if (result) {
    return iconList[index]  
  } 
  
  // md 文件
  var mdList = ['md']
  result = mdList.some(function (item) {
    index = iconList.findIndex((val) => val === 'other.ico') 
    return item === suffix
  })
  if (result) {
    return iconList[index]  
  }   
}
/**
 * 文件类型匹配文字
 * @param {String} fileName [文件名字]
 */
export function matchFileType(fileName) {
  var suffix = getFileSuffix(fileName)
  var result = ''
  // 文件图标列表
  var index = null
   // txt 文件
  var txtList = ['txt', 'TXT']
  result = txtList.some(function (item) {
    index = iconList.findIndex((val) => val === 'txt.ico') 
    return item === suffix
  })
  if (result) {
    return 'txt'
  }

  // pdf 文件
  var pdfList = ['pdf', 'PDF']
  result = pdfList.some(function (item) {
    index = iconList.findIndex((val) => val === 'pdf.ico') 
    return item === suffix
  }) 
  if (result) {
    return 'pdf' 
  }

  // ppt 文件
  var pptList = ['ppt', 'pptx']
  result = pptList.some(function (item) {
    index = iconList.findIndex((val) => val === 'ppt.ico') 
    return item === suffix
  }) 
  if (result) {
    return 'ppt'  
  }

  // word 文件
  var wordList = ['doc', 'docx']
  result = wordList.some(function (item) {
    index = iconList.findIndex((val) => val === 'word.ico') 
    return item === suffix
  })
  if (result) {
    return 'word' 
  } 

  // excel 文件
  var excelList = ['xls', 'xlsx']
  result = excelList.some(function (item) {
    index = iconList.findIndex((val) => val === 'excel.ico') 
    return item === suffix
  }) 
  if (result) {
    return 'excel' 
  }

  // note 文件
  var noteList = ['note']
  result = noteList.some(function (item) {
    index = iconList.findIndex((val) => val === 'note.ico') 
    return item === suffix
  })
  if (result) {
    return 'note'  
  }  
  
  // md 文件
  var mdList = ['md']
  result = mdList.some(function (item) {
    index = iconList.findIndex((val) => val === 'md.ico') 
    return item === suffix
  })
  if (result) {
    return 'md'  
  } 
}
/**
 * 文件名后缀
 * @param {String} fileName [文件名字]
 */
function getFileSuffix(fileName) {
  var suffix = ''
  try {
    var fileArr = fileName.split('.')
    suffix = fileArr[fileArr.length - 1]
  } catch (error) {
    suffix = ''
  }
  // console.log("======suffix=======", suffix)
  if(!suffix) {
    return false
  } else {
    return suffix
  }
}