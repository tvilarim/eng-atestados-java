import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
})
export class UploadComponent {
  selectedFile: File | null = null;
  extractedData: any;

  constructor(private http: HttpClient) {}

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onUpload(): void {
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      this.http.post('http://backend-service/api/pdf/upload', formData).subscribe(
        (data) => {
          this.extractedData = data;
        },
        (error) => {
          console.error('Error uploading PDF', error);
        }
      );
    }
  }
}