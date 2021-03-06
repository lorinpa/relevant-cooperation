"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var mlButtonMod_1 = require("../controls/button/mlButtonMod");
var mlDialog_1 = require("./mlDialog");
var MlDialogMod = (function () {
    function MlDialogMod() {
    }
    MlDialogMod = __decorate([
        core_1.NgModule({
            imports: [mlButtonMod_1.MlButtonMod],
            declarations: [mlDialog_1.MlDialog, mlDialog_1.MlDialogTitle, mlDialog_1.MlDialogContent, mlDialog_1.MlDialogActions],
            exports: [mlButtonMod_1.MlButtonMod, mlDialog_1.MlDialog, mlDialog_1.MlDialogTitle, mlDialog_1.MlDialogContent, mlDialog_1.MlDialogActions] }), 
        __metadata('design:paramtypes', [])
    ], MlDialogMod);
    return MlDialogMod;
}());
exports.MlDialogMod = MlDialogMod;
