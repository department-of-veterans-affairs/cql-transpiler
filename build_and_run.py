import subprocess
import os
from render_models import render_models
import tkinter as tk
from tkinter import messagebox, filedialog
import threading
import itertools
import time

MODEL_ORDER_FILE_NAME = "model_order.txt"

def run_command(command):
    process = subprocess.Popen(command, shell=True, text=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    output = ""
    for line in process.stdout:
        output_text.insert(tk.END, line)
        output_text.see(tk.END)
        output += line
    for line in process.stderr:
        output_text.insert(tk.END, line)
        output_text.see(tk.END)
        output += line
    process.wait()
    if process.returncode != 0:
        output += f"\nCommand failed with return code {process.returncode}"
        return False, output
    return True, output

def check_jar_exists():
    return os.path.exists(jar_path)

def compile_java():
    def task():
        output_text.delete(1.0, tk.END)
        output_text.insert(tk.END, "Compiling Java...\n")
        success, output = run_command("gradlew clean build")
        output_text.insert(tk.END, output)
        if not success:
            messagebox.showerror("Error", "Compilation failed")
        else:
            update_ui_state()
        compile_loading_textbox.delete(1.0, tk.END)
        enable_all_buttons()
    disable_all_buttons()
    threading.Thread(target=task).start()
    threading.Thread(target=show_loading, args=(compile_loading_textbox,)).start()

def update_ui_state():
    jar_exists = check_jar_exists()
    compile_button.config(state=tk.NORMAL if not jar_exists else tk.DISABLED)
    recompile_button.config(state=tk.NORMAL if jar_exists else tk.DISABLED)
    render_button.config(state=tk.NORMAL if jar_exists else tk.DISABLED)
    render_models_button.config(state=tk.NORMAL if jar_exists and os.path.exists(os.path.join(jinja_folder.get(), MODEL_ORDER_FILE_NAME)) else tk.DISABLED)

def browse_folder(entry):
    folder_selected = filedialog.askdirectory()
    if folder_selected:
        entry.delete(0, tk.END)
        entry.insert(0, folder_selected + '/')

def build_project():
    def task():
        output_text.delete(1.0, tk.END)
        output_text.insert(tk.END, "Building the project...\n")
        success, output = run_command("gradlew clean build")
        output_text.insert(tk.END, output)
        if not success:
            messagebox.showerror("Error", "Build failed")
            return

        if not check_jar_exists():
            messagebox.showerror("Error", f"Jar file not found: {jar_path}")
            return

        output_text.insert(tk.END, "Running the Transpiler...\n")
        cql_libraries_to_transpile = "CMS104-v12-0-000-QDM-5-6.cql,CMS506v7/CMS506-v7-0-000-QDM-5-6.cql"
        java_command = (
            f"java -cp {jar_path} gov.va.transpiler.jinja.Transpiler "
            f"librarySource={cql_source_folder.get()} "
            f"jinjaTarget={jinja_folder.get()} "
            f"modelOrderFilePath={os.path.join(jinja_folder.get(), MODEL_ORDER_FILE_NAME)} "
            f"targetLanguage=sparksql "
            f"printFunctions=false "
            f"cqlLibrariesToTranspile={cql_libraries_to_transpile}"
        )
        success, output = run_command(java_command)
        output_text.insert(tk.END, output)
        if not success:
            messagebox.showerror("Error", "Transpiler execution failed")
            return
        update_ui_state()
        render_loading_textbox.delete(1.0, tk.END)
        enable_all_buttons()
    disable_all_buttons()
    threading.Thread(target=task).start()
    threading.Thread(target=show_loading, args=(render_loading_textbox,)).start()

def render_jinja_files():
    def task():
        output_text.delete(1.0, tk.END)
        output_text.insert(tk.END, "Invoking render_models function...\n")
        render_models(jinja_folder.get(), target_folder.get(), os.path.join(jinja_folder.get(), MODEL_ORDER_FILE_NAME), skip_failing_files.get(), autoformat.get())
        output_text.insert(tk.END, "Render models completed.\n")
        render_models_loading_textbox.delete(1.0, tk.END)
        enable_all_buttons()
    disable_all_buttons()
    threading.Thread(target=task).start()
    threading.Thread(target=show_loading, args=(render_models_loading_textbox,)).start()

def show_loading(textbox):
    spinner = itertools.cycle(['|', '/', '-', '\\'])
    while threading.active_count() > 2:
        textbox.delete(1.0, tk.END)
        textbox.insert(tk.END, next(spinner))
        time.sleep(0.1)
    textbox.delete(1.0, tk.END)

def disable_all_buttons():
    compile_button.config(state=tk.DISABLED)
    recompile_button.config(state=tk.DISABLED)
    render_button.config(state=tk.DISABLED)
    render_models_button.config(state=tk.DISABLED)
    browse_cql_button.config(state=tk.DISABLED)
    browse_jinja_button.config(state=tk.DISABLED)
    browse_target_button.config(state=tk.DISABLED)
    skip_failing_files_checkbutton.config(state=tk.DISABLED)
    autoformat_checkbutton.config(state=tk.DISABLED)

def enable_all_buttons():
    update_ui_state()
    browse_cql_button.config(state=tk.NORMAL)
    browse_jinja_button.config(state=tk.NORMAL)
    browse_target_button.config(state=tk.NORMAL)
    skip_failing_files_checkbutton.config(state=tk.NORMAL)
    autoformat_checkbutton.config(state=tk.NORMAL)

def create_gui():
    global output_text, compile_button, recompile_button, render_button, render_models_button
    global cql_source_folder, jinja_folder, target_folder
    global skip_failing_files, autoformat
    global compile_loading_textbox, render_loading_textbox, render_models_loading_textbox
    global browse_cql_button, browse_jinja_button, browse_target_button
    global skip_failing_files_checkbutton, autoformat_checkbutton

    root = tk.Tk()
    root.title("CQL Transpiler")

    frame = tk.Frame(root)
    frame.pack(padx=10, pady=10)

    cql_source_folder = tk.Entry(frame, width=50)
    cql_source_folder.pack(pady=5)
    cql_source_folder.insert(0, "resources/cql/")
    browse_cql_button = tk.Button(frame, text="Browse CQL Source Folder", command=lambda: browse_folder(cql_source_folder))
    browse_cql_button.pack(pady=5)

    jinja_folder = tk.Entry(frame, width=50)
    jinja_folder.pack(pady=5)
    jinja_folder.insert(0, "resources/jinja/")
    browse_jinja_button = tk.Button(frame, text="Browse Jinja Folder", command=lambda: browse_folder(jinja_folder))
    browse_jinja_button.pack(pady=5)

    target_folder = tk.Entry(frame, width=50)
    target_folder.pack(pady=5)
    target_folder.insert(0, "resources/transpiler_dbt_models/")
    browse_target_button = tk.Button(frame, text="Browse Target Folder", command=lambda: browse_folder(target_folder))
    browse_target_button.pack(pady=5)

    skip_failing_files = tk.BooleanVar()
    skip_failing_files_checkbutton = tk.Checkbutton(frame, text="Skip Failing Files", variable=skip_failing_files)
    skip_failing_files_checkbutton.pack(pady=5)

    autoformat = tk.BooleanVar(value=True)
    autoformat_checkbutton = tk.Checkbutton(frame, text="Autoformat", variable=autoformat)
    autoformat_checkbutton.pack(pady=5)

    compile_frame = tk.Frame(frame)
    compile_frame.pack(pady=5)
    compile_button = tk.Button(compile_frame, text="Compile Java", command=compile_java)
    compile_button.pack(side=tk.LEFT)
    compile_loading_textbox = tk.Text(compile_frame, height=1, width=2)
    compile_loading_textbox.pack(side=tk.LEFT)

    recompile_frame = tk.Frame(frame)
    recompile_frame.pack(pady=5)
    recompile_button = tk.Button(recompile_frame, text="Recompile Java", command=compile_java)
    recompile_button.pack(side=tk.LEFT)
    recompile_loading_textbox = tk.Text(recompile_frame, height=1, width=2)
    recompile_loading_textbox.pack(side=tk.LEFT)

    render_frame = tk.Frame(frame)
    render_frame.pack(pady=5)
    render_button = tk.Button(render_frame, text="Render Intermediate Files", command=build_project)
    render_button.pack(side=tk.LEFT)
    render_loading_textbox = tk.Text(render_frame, height=1, width=2)
    render_loading_textbox.pack(side=tk.LEFT)

    render_models_frame = tk.Frame(frame)
    render_models_frame.pack(pady=5)
    render_models_button = tk.Button(render_models_frame, text="Render Jinja Files", command=render_jinja_files)
    render_models_button.pack(side=tk.LEFT)
    render_models_loading_textbox = tk.Text(render_models_frame, height=1, width=2)
    render_models_loading_textbox.pack(side=tk.LEFT)

    output_text = tk.Text(frame, height=20, width=80)
    output_text.pack(pady=5)

    update_ui_state()
    root.mainloop()

if __name__ == "__main__":
    jar_path = "transpiler/build/libs/transpiler-1.0-SNAPSHOT.jar"
    create_gui()
