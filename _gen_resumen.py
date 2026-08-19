import os,re,glob,pathlib
base=r'c:\curso-programas.etc\spring tool\proyectos-spreng\demo\src\main\java\com\drover\demo\backend'
sections=['dto','entity','exception','mapper','repository','service','controller']
java_files=[]
for sec in sections:
    java_files.extend(glob.glob(os.path.join(base,sec,'**','*.java'),recursive=True))
java_files=sorted(java_files)
mods=[]
field_pat=re.compile(r'^(?:private|protected|public)\s+(?!class|interface|enum)([\w<>\[\], ?]+)\s+(\w+)\s*(?:=.*)?;')
method_pat=re.compile(r'^(?:public|protected|private)\s+(?:static\s+)?(?:[\w<>\[\], ?]+)\s+(\w+)\s*\(([^)]*)\)\s*(?:\{|throws|;)')
for fp in java_files:
    rel=os.path.relpath(fp,base).replace('\\','/')
    sec=rel.split('/')[0]
    lines=open(fp,encoding='utf-8').read().splitlines()
    class_name=pathlib.Path(fp).stem
    fields=[]; methods=[]
    for ln in lines:
        s=ln.strip()
        if s.startswith('//') or s.startswith('*') or s.startswith('@'):
            continue
        m=field_pat.match(s)
        if m:
            t,n=m.group(1).strip(),m.group(2)
            fields.append((t,n)); continue
        mm=method_pat.match(s)
        if mm:
            methods.append((mm.group(1),mm.group(2).strip()))
    getters=[m for m,_ in methods if m.startswith('get') or m.startswith('is')]
    setters=[m for m,_ in methods if m.startswith('set')]
    others=[(m,p) for m,p in methods if m not in getters and m not in setters]
    mods.append({'rel':rel,'section':sec,'class':class_name,'fields':fields,'getters':getters,'setters':setters,'others':others})
by={s:[] for s in sections}
for m in mods: by[m['section']].append(m)
md=[]
md.append('# Resumen del Backend\n')
md.append('Este documento resume DTO (request/response), Entity, Exception, Mapper, Repository, Service y Controller.\n')
for sec in sections:
    arr=by[sec]
    md.append('## {} ({} archivos)\n'.format(sec.upper(),len(arr)))
    for m in arr:
        md.append('### {} (`{}`)'.format(m['class'],m['rel']))
        if m['fields']:
            md.append('- Atributos: ' + ', '.join(['`{}: {}`'.format(n,t) for t,n in m['fields']]))
        else:
            md.append('- Atributos: no se detectaron atributos propios (o estan generados por Lombok).')
        md.append('- Getters: ' + (', '.join(['`{}()`'.format(g) for g in m['getters']]) if m['getters'] else 'no explicitos en el archivo.'))
        md.append('- Setters: ' + (', '.join(['`{}()`'.format(s) for s in m['setters']]) if m['setters'] else 'no explicitos en el archivo.'))
        if m['others']:
            md.append('- Funciones principales: ' + ', '.join(['`{}({})`'.format(n,p) if p else '`{}()`'.format(n) for n,p in m['others']]))
        else:
            md.append('- Funciones principales: sin metodos adicionales explicitos.')
        md.append('')
md_text='\n'.join(md)
out_md=r'c:\curso-programas.etc\spring tool\proyectos-spreng\demo\resumen-backend.md'
open(out_md,'w',encoding='utf-8').write(md_text)
def esc(t):
    t=t.replace('\\','\\\\').replace('{','\\{').replace('}','\\}')
    return ''.join((c if ord(c)<128 else '\\u{}?'.format(ord(c))) for c in t)
rtf=['{\\rtf1\\ansi\\deff0','{\\fonttbl{\\f0 Calibri;}}','\\fs22']
for line in md_text.split('\n'):
    if line.startswith('# '): rtf.append('\\b '+esc(line[2:])+'\\b0\\par')
    elif line.startswith('## '): rtf.append('\\b '+esc(line[3:])+'\\b0\\par')
    elif line.startswith('### '): rtf.append('\\b '+esc(line[4:])+'\\b0\\par')
    elif line.startswith('- '): rtf.append('\\tab '+esc(line)+'\\par')
    else: rtf.append(esc(line)+'\\par')
rtf.append('}')
out_rtf=r'c:\curso-programas.etc\spring tool\proyectos-spreng\demo\resumen-backend.rtf'
open(out_rtf,'w',encoding='utf-8').write('\n'.join(rtf))
print('OK',len(java_files))
print(out_md)
print(out_rtf)
