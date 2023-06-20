package gov.va.sparkcql.core.io

import java.io.File

object Files {
  def currentDir() = {
    System.getProperty("user.dir")
  }
  
  def search(path: String, ext: String = "*"): Array[String] = {
    val fileList = recursiveListFiles(new File(path)).filter(!_.isDirectory())
    val extFilter = if (ext != "*") { fileList.filter(_.getName.endsWith(ext)) } else { fileList }
    extFilter.map(_.getAbsolutePath())  
  }

  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    if (these != null) {
      these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
    } else {
      Array[File]()
    }
  }
}
