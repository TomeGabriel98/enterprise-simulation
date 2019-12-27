package br.gov.sp.fatec.logo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.service.UploadFileResponse;

@RestController
@CrossOrigin
@RequestMapping(value="/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private DBFileStorageService DBFileStorageService;
    
    @Autowired
    private EmpresaServiceImpl empresaService;
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = DBFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/updateFile/{id}")
    public DBFile updateFile(@RequestParam("file") MultipartFile file, @PathVariable Integer id) throws IOException {
    	Empresa empresa = empresaService.buscarPorId(id).get();
    	DBFile dbFile = DBFileStorageService.getFile(empresa.getLogo());
    	dbFile.setData(file.getBytes());    	
    	return DBFileStorageService.update(dbFile);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<String> downloadFile(@PathVariable String fileId) throws IOException {
        // Load file from database
        DBFile dbFile = DBFileStorageService.getFile(fileId);
        ByteArrayResource b = new ByteArrayResource(dbFile.getData());
        byte[] bs4 = Base64.getEncoder().encode(b.getByteArray());
        System.out.println(new String(bs4));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new String(bs4));
    }
    
    @GetMapping("/downloadFile/all")
    public ResponseEntity<Collection<DBFile>> getAll() {
		return new ResponseEntity<Collection<DBFile>>(DBFileStorageService.getAll(), HttpStatus.OK);
	}
    
}