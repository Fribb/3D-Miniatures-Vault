import { Component } from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {UtilsService} from "../shared/utils.service";

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {

  public appVersion: string = "";

  constructor(private utilsService: UtilsService) {
    this.appVersion = this.utilsService.getAppVersion();
  }

}
