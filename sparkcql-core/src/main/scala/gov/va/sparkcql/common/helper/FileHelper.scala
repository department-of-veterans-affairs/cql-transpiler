package gov.va.sparkcql.common.helper

import java.io.File

object FileHelper {
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
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }
}
